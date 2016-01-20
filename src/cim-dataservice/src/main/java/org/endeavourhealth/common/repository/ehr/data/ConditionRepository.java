package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Condition;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConditionRepository extends Repository {
	private static final String TableName = "condition";

	public ConditionRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Condition> getConditionsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Condition> results = new ArrayList<>();

        for (Row row : rows) {
            Condition condition = new Condition();
            condition.setConditionId(row.getUUID("condition_id"));
            condition.setPatientId(row.getUUID("patient_id"));
            condition.setServiceId(row.getUUID("service_id"));
            condition.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            condition.setMetaData(row.getMap("meta_data", String.class, String.class));
            condition.setEntryData(row.getString("entry_data"));
            condition.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(condition);
        }

        return results;
    }

    public List<Condition> getConditionsByPatientServiceId(UUID patientId, UUID serviceId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId))
                .and(QueryBuilder.eq("service_id", serviceId))
        );

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Condition> results = new ArrayList<>();

        for (Row row : rows) {
            Condition condition = new Condition();
            condition.setConditionId(row.getUUID("condition_id"));
            condition.setPatientId(row.getUUID("patient_id"));
            condition.setServiceId(row.getUUID("service_id"));
            condition.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            condition.setMetaData(row.getMap("meta_data", String.class, String.class));
            condition.setEntryData(row.getString("entry_data"));
            condition.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(condition);
        }

        return results;
    }

    public List<Condition> getConditionsByPatientStatus(UUID patientId, String status) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                        .all()
                        .from(TableName)
                        .where(QueryBuilder.eq("patient_id", patientId))
                        .and(QueryBuilder.eq("meta_data['status']", status))
        );

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Condition> results = new ArrayList<>();

        for (Row row : rows) {
            Condition condition = new Condition();
            condition.setConditionId(row.getUUID("condition_id"));
            condition.setPatientId(row.getUUID("patient_id"));
            condition.setServiceId(row.getUUID("service_id"));
            condition.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            condition.setMetaData(row.getMap("meta_data", String.class, String.class));
            condition.setEntryData(row.getString("entry_data"));
            condition.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(condition);
        }

        return results;
    }

    public void add(Condition condition) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("condition_id", condition.getConditionId())
                .addColumnUUID("patient_id", condition.getPatientId())
                .addColumnUUID("service_id", condition.getServiceId())
                .addColumnTimestamp("effective_datetime", condition.getEffectiveDateTime())
                .addColumnMap("meta_data", condition.getMetaData())
                .addColumnString("entry_data", condition.getEntryData())
                .addColumnTimestamp("last_updated", condition.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
