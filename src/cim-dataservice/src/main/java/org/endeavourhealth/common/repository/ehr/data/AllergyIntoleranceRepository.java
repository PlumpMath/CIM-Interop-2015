package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.AllergyIntolerance;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AllergyIntoleranceRepository extends Repository {
	private static final String TableName = "allergy_intolerance";

	public AllergyIntoleranceRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<AllergyIntolerance> getAllergyIntolerancesByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<AllergyIntolerance> results = new ArrayList<>();

        for (Row row : rows) {
            AllergyIntolerance allergyIntolerance = new AllergyIntolerance();
            allergyIntolerance.setAllergyIntoleranceId(row.getUUID("allergy_intolerance_id"));
            allergyIntolerance.setPatientId(row.getUUID("patient_id"));
            allergyIntolerance.setServiceId(row.getUUID("service_id"));
            allergyIntolerance.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            allergyIntolerance.setMetaData(row.getMap("meta_data", String.class, String.class));
            allergyIntolerance.setEntryData(row.getString("entry_data"));
            allergyIntolerance.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(allergyIntolerance);
        }

        return results;
    }

    public List<AllergyIntolerance> getAllergyIntolerancesByPatientServiceId(UUID patientId, UUID serviceId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId))
                .and(QueryBuilder.eq("service_id", serviceId))
        );

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<AllergyIntolerance> results = new ArrayList<>();

        for (Row row : rows) {
            AllergyIntolerance allergyIntolerance = new AllergyIntolerance();
            allergyIntolerance.setAllergyIntoleranceId(row.getUUID("allergy_intolerance_id"));
            allergyIntolerance.setPatientId(row.getUUID("patient_id"));
            allergyIntolerance.setServiceId(row.getUUID("service_id"));
            allergyIntolerance.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            allergyIntolerance.setMetaData(row.getMap("meta_data", String.class, String.class));
            allergyIntolerance.setEntryData(row.getString("entry_data"));
            allergyIntolerance.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(allergyIntolerance);
        }

        return results;
    }

    public void add(AllergyIntolerance allergyIntolerance) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("allergy_intolerance_id", allergyIntolerance.getAllergyIntoleranceId())
                .addColumnUUID("patient_id", allergyIntolerance.getPatientId())
                .addColumnUUID("service_id", allergyIntolerance.getServiceId())
                .addColumnTimestamp("effective_datetime", allergyIntolerance.getEffectiveDateTime())
                .addColumnMap("meta_data", allergyIntolerance.getMetaData())
                .addColumnString("entry_data", allergyIntolerance.getEntryData())
                .addColumnTimestamp("last_updated", allergyIntolerance.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
