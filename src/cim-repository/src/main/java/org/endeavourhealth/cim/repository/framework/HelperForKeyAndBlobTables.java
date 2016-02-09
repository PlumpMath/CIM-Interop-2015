package org.endeavourhealth.cim.repository.framework;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HelperForKeyAndBlobTables<T> {

    public interface CreateMainObject<T> {
        public T create(String version, String data) throws RepositoryException;
    }

    public HelperForKeyAndBlobTables(
            Session session,
            PreparedStatementCache statementCache,
            String tableName,
            CreateMainObject<T> creator) {
        this.session = session;
        this.statementCache = statementCache;
        this.tableName = tableName;
        this.creator = creator;
    }

    private final Session session;
    private final PreparedStatementCache statementCache;
    private final String tableName;
    private final CreateMainObject<T> creator;
    private String idColumnName = "id";
    private String schemaVersionColumnName = "schema_version";
    private String dataColumnName = "data";

    public void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    public void setSchemaVersionColumnName(String schemaVersionColumnName) {
        this.schemaVersionColumnName = schemaVersionColumnName;
    }

    public void setDataColumnName(String dataColumnName) {
        this.dataColumnName = dataColumnName;
    }

    public T getById(UUID id) throws RepositoryException {
        Row row = RepositoryHelper.getSingleRowFromId(session, statementCache, tableName, idColumnName, id);
        return getTFromRow(row);
    }

    public List<T> getMultipleById(List<UUID> ids) throws RepositoryException {
        List<Row> rows = RepositoryHelper.getRowsByIds(session, statementCache, tableName, idColumnName, ids);
        return getTListFromRows(rows);
    }

    public List<T> getAll() throws RepositoryException {
        List<Row> rows = RepositoryHelper.getAll(session, statementCache, tableName);
        return getTListFromRows(rows);
    }

    private List<T> getTListFromRows(List<Row> rows) throws RepositoryException {
        List<T> results = new ArrayList<>();

        for (Row row: rows) {
            if (row != null)
                results.add(getTFromRow(row));
        }

        return results;
    }

    private T getTFromRow(Row row) throws RepositoryException {
        if (row == null)
            return null;

        return creator.create(row.getString(schemaVersionColumnName), row.getString(dataColumnName));
    }
}

