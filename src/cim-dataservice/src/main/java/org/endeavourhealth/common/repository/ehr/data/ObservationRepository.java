package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Observation;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObservationRepository extends Repository {
	private static final String TableName = "observation";

	public ObservationRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Observation> getObservationsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Observation> results = new ArrayList<>();

        for (Row row : rows) {
            Observation observation = new Observation();
            observation.setObservationId(row.getUUID("observation_id"));
            observation.setPatientId(row.getUUID("patient_id"));
            observation.setServiceId(row.getUUID("service_id"));
            observation.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            observation.setMetaData(row.getMap("meta_data", String.class, String.class));
            observation.setEntryData(row.getString("entry_data"));
            observation.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(observation);
        }

        return results;
    }

    public List<Observation> getObservationsByPatientServiceId(UUID patientId, UUID serviceId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId))
                .and(QueryBuilder.eq("service_id", serviceId))
        );

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Observation> results = new ArrayList<>();

        for (Row row : rows) {
            Observation observation = new Observation();
            observation.setObservationId(row.getUUID("observation_id"));
            observation.setPatientId(row.getUUID("patient_id"));
            observation.setServiceId(row.getUUID("service_id"));
            observation.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            observation.setMetaData(row.getMap("meta_data", String.class, String.class));
            observation.setEntryData(row.getString("entry_data"));
            observation.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(observation);
        }

        return results;
    }

    public void add(Observation observation) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("observation_id", observation.getObservationId())
                .addColumnUUID("patient_id", observation.getPatientId())
                .addColumnUUID("service_id", observation.getServiceId())
                .addColumnTimestamp("effective_datetime", observation.getEffectiveDateTime())
                .addColumnMap("meta_data", observation.getMetaData())
                .addColumnString("entry_data", observation.getEntryData())
                .addColumnTimestamp("last_updated", observation.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
