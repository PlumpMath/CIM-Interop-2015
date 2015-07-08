package org.endeavourhealth.cim.common.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.querybuilder.*;

import java.util.Date;
import java.util.UUID;

public class DeleteStatementBuilder extends BoundStatementBuilder {

    private final FieldAndValueList parameters = new FieldAndValueList();
    private final Clause[] clauses;

    public DeleteStatementBuilder(PreparedStatementCache statementCache, String tableName, Clause clause) {
        this(statementCache, tableName, new Clause[] { clause });
    }

    public DeleteStatementBuilder(PreparedStatementCache statementCache, String tableName, Clause[] clauses) {
        super(statementCache, tableName);
        this.clauses = clauses;
    }

    public DeleteStatementBuilder addParameterUUID(String columnName, UUID value) {
        parameters.add(FieldType.UUID, columnName, value);
        return this;
    }

    public DeleteStatementBuilder addParameterString(String columnName, String value) {
        parameters.add(FieldType.String, columnName, value);
        return this;
    }

    public DeleteStatementBuilder addParameterTimestamp(String columnName, Date value) {
        parameters.add(FieldType.Timestamp, columnName, value);
        return this;
    }

    public BoundStatement build() {

        Delete.Where delete = QueryBuilder.delete()
            .from(super.tableName)
            .where(clauses[0]);

        //start at index 1
        for (int i = 1; i < clauses.length; i++) {
            delete = delete.and(clauses[i]);
        }

        return super.build(delete, parameters);
    }
}