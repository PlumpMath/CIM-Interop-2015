package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Encounter;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EncounterRepository extends Repository {
	private static final String TableName = "encounter";

	public EncounterRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Encounter> getEncountersByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Encounter> results = new ArrayList<>();

        for (Row row : rows) {
            Encounter encounter = new Encounter();
            encounter.setEncounterId(row.getUUID("encounter_id"));
            encounter.setPatientId(row.getUUID("patient_id"));
            encounter.setServiceId(row.getUUID("service_id"));
            encounter.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            encounter.setMetaData(row.getMap("meta_data", String.class, String.class));
            encounter.setEntryData(row.getString("entry_data"));
            encounter.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(encounter);
        }

        return results;
    }

    public List<Encounter> getEncountersByPatientServiceId(UUID patientId, UUID serviceId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId))
                .and(QueryBuilder.eq("service_id", serviceId))
        );

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Encounter> results = new ArrayList<>();

        for (Row row : rows) {
            Encounter encounter = new Encounter();
            encounter.setEncounterId(row.getUUID("encounter_id"));
            encounter.setPatientId(row.getUUID("patient_id"));
            encounter.setServiceId(row.getUUID("service_id"));
            encounter.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            encounter.setMetaData(row.getMap("meta_data", String.class, String.class));
            encounter.setEntryData(row.getString("entry_data"));
            encounter.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(encounter);
        }

        return results;
    }

    public void add(Encounter encounter) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("encounter_id", encounter.getEncounterId())
                .addColumnUUID("patient_id", encounter.getPatientId())
                .addColumnUUID("service_id", encounter.getServiceId())
                .addColumnTimestamp("effective_datetime", encounter.getEffectiveDateTime())
                .addColumnMap("meta_data", encounter.getMetaData())
                .addColumnString("entry_data", encounter.getEntryData())
                .addColumnTimestamp("last_updated", encounter.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
