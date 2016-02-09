package org.endeavourhealth.cim.repository.domains.audit;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.cim.repository.utils.DeserializationException;
import org.endeavourhealth.cim.repository.utils.JsonSerializer;
import org.endeavourhealth.cim.repository.framework.InsertStatementBuilder;
import org.endeavourhealth.cim.repository.framework.PreparedStatementCache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AuditHelper {
    public static final String AUDIT_TABLE = "audit";

    public static BoundStatement buildAuditStatement(
            PreparedStatementCache statementCache,
            String tableName,
            UUID rowKey,
            UUID userId,
            AuditMode mode,
            String schemaVersion,
            String auditData) {

        UUID auditId = UUID.randomUUID();
        Date auditDate = new Date();

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, AUDIT_TABLE)
                .addColumnUUID("id", auditId)
                .addColumnString("table_name", tableName)
                .addColumnUUID("row_key", rowKey)
                .addColumnTimestamp("audit_date", auditDate)
                .addColumnUUID("user_id", userId)
                .addColumnString("mode", mode.toString())
                .addColumnString("schema_version", schemaVersion)
                .addColumnString("data", auditData)
                .build();

        return boundStatement;
    }

    public static <T> List<AuditItem<T>> getAuditItems(Session session,
            PreparedStatementCache statementCache,
            String tableName,
            UUID rowKey,
            Class<T> valueType) throws DeserializationException
    {

        PreparedStatement preparedStatement = statementCache.getOrAdd(QueryBuilder.select()
                .all()
                .from(AUDIT_TABLE)
                .where(QueryBuilder.eq("table_name", QueryBuilder.bindMarker("table_name")))
                .and(QueryBuilder.eq("row_key", QueryBuilder.bindMarker("row_key"))));

        BoundStatement boundStatement = preparedStatement
                .bind()
                .setString("table_name", tableName)
                .setUUID("row_key", rowKey);

        List<Row> rows =  session.execute(boundStatement)
                .all();

        List<AuditItem<T>> results = new ArrayList<>();

        for (Row row : rows) {
            AuditItem<T> auditItem = new AuditItem<>();
            auditItem.setId(row.getUUID("id"));
            auditItem.setTableName(row.getString("table_name"));
            auditItem.setRowKey(row.getUUID("row_key"));
            auditItem.setAuditDate(row.getDate("audit_date"));
            auditItem.setUserId(row.getUUID("user_id"));
            auditItem.setMode(AuditMode.valueOf(row.getString("mode")));
            auditItem.setData(JsonSerializer.deserialize(valueType, row.getString("data")));

            results.add(auditItem);
        }

        return results;
    }
}
