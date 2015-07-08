package org.endeavourhealth.cim.common.data;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
//import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HelperForSearchTermTables {

    //does not include hyphen or apostrophe
    private static final String[] charactersToSplitPhrasesOn = {
            "\\s",
            ",",
            "\\.",
            ":",
            ";",
            "\\?",
            "!",
            "\\[",
            "\\]",
            "\\(",
            "\\)"
    };

    private static final String regexToSplitPhrasesOn;

    static {
        regexToSplitPhrasesOn = StringUtils.join(charactersToSplitPhrasesOn, "|");
    }

    public HelperForSearchTermTables(
            Session session,
            PreparedStatementCache statementCache,
            String tableName,
            String sourceIdColumnName) {
        this.session = session;
        this.statementCache = statementCache;
        this.tableName = tableName;
        this.sourceIdColumnName = sourceIdColumnName;

    }

    private final Session session;
    private final PreparedStatementCache statementCache;
    private final String tableName;
    private final String sourceIdColumnName;
    private String searchTermColumnName = "search_term";
    private String termTypeColumnName = "term_type";

    public void setTermTypeColumnName(String termTypeColumnName) {
        this.termTypeColumnName = termTypeColumnName;
    }

    public String getTermTypeColumnName() {
        return termTypeColumnName;
    }

    public String getSearchTermColumnName() {
        return searchTermColumnName;
    }

    public void setSearchTermColumnName(String searchTermColumnName) {
        this.searchTermColumnName = searchTermColumnName;
    }

    public void insert(BatchStatement batch, UUID identifier, List<SearchPair> searchPairs) {
        if (batch == null)
            throw new IllegalArgumentException("batch is null");

        if (identifier == null)
            throw new IllegalArgumentException("identifier is null");

        if (searchPairs == null)
            return;

        for (SearchPair pair : searchPairs) {
            addSearchPair(batch, identifier, pair);
        }
    }

    private void addSearchPair(BatchStatement batch, UUID identifier, SearchPair pair) {

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, tableName)
                .addColumnString(getTermTypeColumnName(), pair.getTermType().toLowerCase())
                .addColumnString(getSearchTermColumnName(), pair.getSearchTerm().toLowerCase())
                .addColumnUUID(sourceIdColumnName, identifier)
                .build();

        batch.add(boundStatement);
    }

    private void deleteSearchPair(BatchStatement batch, UUID identifier, SearchPair pair) {
        Clause[] clauses = {
                QueryBuilder.eq(getTermTypeColumnName(), QueryBuilder.bindMarker(getTermTypeColumnName())),
                QueryBuilder.eq(getSearchTermColumnName(), QueryBuilder.bindMarker(getSearchTermColumnName())),
                QueryBuilder.eq(sourceIdColumnName, QueryBuilder.bindMarker(sourceIdColumnName))};

        BoundStatement boundStatement = new DeleteStatementBuilder(statementCache, tableName, clauses)
                .addParameterString(getTermTypeColumnName(), pair.getTermType().toLowerCase())
                .addParameterString(getSearchTermColumnName(), pair.getSearchTerm().toLowerCase())
                .addParameterUUID(sourceIdColumnName, identifier)
                .build();

        batch.add(boundStatement);
    }

    public List<UUID> search(String termType, List<String> searchTerms) throws RepositoryException {
        List<SearchPair> pairs = searchTerms.stream()
                .map(s -> new SearchPair(termType, s))
                .collect(Collectors.toList());

        return search(pairs);
    }

    public List<UUID> search(List<SearchPair> searchPairs) throws RepositoryException {
        if (searchPairs == null || searchPairs.size() == 0)
            throw new IllegalArgumentException("searchPairs is null or empty");

        if (searchPairs.size() == 1)
            return search(searchPairs.get(0));

        List<UUID> ids = search(searchPairs.get(0));

        //start at 1
        for (int i = 1; i < searchPairs.size(); i++) {

            if (ids.size() == 0)
                break;

            List<UUID> tempIds = search(searchPairs.get(i));
            ids.retainAll(tempIds);
        }

        return ids;
    }

    public List<UUID> search(SearchPair searchPair) throws RepositoryException {
        return search(searchPair.getTermType(), searchPair.getSearchTerm());
    }

    public List<UUID> search(String termType, String searchTerm) throws RepositoryException {
        if (StringUtils.isBlank(termType))
            throw new IllegalArgumentException("termType is null or empty");

        if (StringUtils.isBlank(searchTerm))
            throw new IllegalArgumentException("searchTerm is null or empty");

        BoundStatement boundStatement = getBoundStatementToSearchWith(termType, searchTerm);

        List<UUID> ids = session.execute(boundStatement)
                .all()
                .stream()
                .map(row -> row.getUUID(sourceIdColumnName))
                .collect(Collectors.toList());

        return ids;
    }

    private BoundStatement getBoundStatementToSearchWith(String termType, String searchTerm) {

        int asteriskCount = StringUtils.countMatches(searchTerm, "*");

        if (asteriskCount == 0)
            return getBoundStatementForSimpleWord(termType, searchTerm);
        else if (asteriskCount > 1)
            throw new IllegalArgumentException("Search term cannot contain multiple asterisks");
        else if (searchTerm.endsWith("*") == false)
            throw new IllegalArgumentException("If search term contains an asterisk them it must be at the end");
        else if (searchTerm.equals("*"))
            throw new IllegalArgumentException("Search term cannot just be an asterisk");
        else
            return getBoundStatementForWordRange(termType, searchTerm);
    }

    private BoundStatement getBoundStatementForSimpleWord(String termType, String searchTerm) {
        PreparedStatement preparedStatement = statementCache.getOrAdd(QueryBuilder.select()
                .column(sourceIdColumnName)
                .from(tableName)
                .where(QueryBuilder.eq(termTypeColumnName, QueryBuilder.bindMarker(termTypeColumnName)))
                .and(QueryBuilder.eq(searchTermColumnName, QueryBuilder.bindMarker(searchTermColumnName))));

        return preparedStatement
                .bind()
                .setString(termTypeColumnName, termType.toLowerCase())
                .setString(searchTermColumnName, searchTerm.toLowerCase());
    }

    /*
    If the search term is:   test*
    This will search:  where field >= 'test' and field < 'tesu'
     */
    private BoundStatement getBoundStatementForWordRange(String termType, String searchTerm) {

        RegularStatement query = QueryBuilder.select()
                .column(sourceIdColumnName)
                .from(tableName)
                .where(QueryBuilder.eq(termTypeColumnName, QueryBuilder.bindMarker(termTypeColumnName)))
                .and(QueryBuilder.gte(searchTermColumnName, QueryBuilder.bindMarker("from_term")))
                .and(QueryBuilder.lt(searchTermColumnName, QueryBuilder.bindMarker("to_tern")));

        PreparedStatement preparedStatement = statementCache.getOrAdd(query);

        String from = searchTerm.toLowerCase().substring(0, searchTerm.length() - 1);  //remove the asterisk as the end
        String to = createSearchTo(from);

        return preparedStatement
                .bind()
                .setString(termTypeColumnName, termType.toLowerCase())
                .setString("from_term", from)
                .setString("to_tern", to);
    }

    private String createSearchTo(String from) {
        char lastChar = from.charAt(from.length() - 1);
        char newLastChar = (char)(lastChar + 1);

        return from.substring(0, from.length() - 1) + newLastChar;
    }

    public void updateSearchTerms(BatchStatement batch, UUID id, List<SearchPair> currentIdentifiers, List<SearchPair> newIdentifiers) {
        boolean currentIsNullOrEmpty = currentIdentifiers == null || currentIdentifiers.size() == 0;
        boolean newIsNullOrEmpty = newIdentifiers == null || newIdentifiers.size() == 0;

        if (currentIsNullOrEmpty && newIsNullOrEmpty) {
            return;
        } else if (currentIsNullOrEmpty) {
            insert(batch, id, newIdentifiers);
            return;
        }
        else if (newIsNullOrEmpty) {
            for(SearchPair pairToDelete : currentIdentifiers) {
                deleteSearchPair(batch, id, pairToDelete);
            }
            return;
        }

        // delete identifiers that have been removed by the update
        for(SearchPair currentIdentifier : currentIdentifiers) {
            if (newIdentifiers.stream().noneMatch(updatedIdentifier -> compareSearchTerms(updatedIdentifier, currentIdentifier))) {
                deleteSearchPair(batch, id, currentIdentifier);
            }
        }

        // insert identifiers that have been added by the update
        for(SearchPair updatedIdentifier : newIdentifiers) {
            if (currentIdentifiers.stream().noneMatch(currentIdentifier -> compareSearchTerms(updatedIdentifier, currentIdentifier))) {
                addSearchPair(batch, id, updatedIdentifier);
            }
        }
    }

    private boolean compareSearchTerms(SearchPair pair, SearchPair pairToCompare) {
        return pair.getSearchTerm().toLowerCase().equals(pairToCompare.getSearchTerm().toLowerCase())
                && pair.getTermType().toLowerCase().equals(pairToCompare.getTermType().toLowerCase());
    }

    public List<SearchPair> splitText(String termType, String textToSplit) {

        String[] tokens = textToSplit.split(regexToSplitPhrasesOn);

        return Arrays.stream(tokens)
                .filter(t -> t.length()!=0)
        .map(t->new SearchPair(termType,t))
        .collect(Collectors.toList());
    }
}
