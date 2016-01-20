package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Patient;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PatientRepository extends Repository {
	private static final String TableName = "patient";

	public PatientRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Patient> getPatientsByServicePatientId(UUID serviceId, UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("service_id", serviceId))
                .and(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Patient> results = new ArrayList<>();

        for (Row row : rows) {
            Patient patient = new Patient();
            patient.setPatientId(row.getUUID("patient_id"));
            patient.setServiceId(row.getUUID("service_id"));
            patient.setMetaData(row.getMap("meta_data", String.class, String.class));
            patient.setEntryData(row.getString("entry_data"));
            patient.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(patient);
        }

        return results;
    }

    public void add(Patient patient) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("patient_id", patient.getPatientId())
                .addColumnUUID("service_id", patient.getServiceId())
                .addColumnMap("meta_data", patient.getMetaData())
                .addColumnString("entry_data", patient.getEntryData())
                .addColumnTimestamp("last_updated", patient.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
