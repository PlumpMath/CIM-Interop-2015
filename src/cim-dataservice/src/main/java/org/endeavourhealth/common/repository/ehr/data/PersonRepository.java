package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Person;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonRepository extends Repository {
	private static final String TableName = "person";

	public PersonRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Person> getPersonsByPersonId(UUID personId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("person_id", personId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Person> results = new ArrayList<>();

        for (Row row : rows) {
            Person person = new Person();
            person.setPersonId(row.getUUID("person_id"));
            person.setMetaData(row.getMap("meta_data", String.class, String.class));
            person.setEntryData(row.getString("entry_data"));
            person.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(person);
        }

        return results;
    }

    public void add(Person person) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("person_id", person.getPersonId())
                .addColumnMap("meta_data", person.getMetaData())
                .addColumnString("entry_data", person.getEntryData())
                .addColumnTimestamp("last_updated", person.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
