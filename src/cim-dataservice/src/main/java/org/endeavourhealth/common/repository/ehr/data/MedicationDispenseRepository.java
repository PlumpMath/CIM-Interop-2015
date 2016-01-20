package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.MedicationDispense;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicationDispenseRepository extends Repository {
	private static final String TableName = "medication_dispense";

	public MedicationDispenseRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<MedicationDispense> getMedicationDispensesByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<MedicationDispense> results = new ArrayList<>();

        for (Row row : rows) {
            MedicationDispense medicationDispense = new MedicationDispense();
            medicationDispense.setMedicationDispenseId(row.getUUID("medication_dispense_id"));
            medicationDispense.setPatientId(row.getUUID("patient_id"));
            medicationDispense.setServiceId(row.getUUID("service_id"));
            medicationDispense.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            medicationDispense.setMetaData(row.getMap("meta_data", String.class, String.class));
            medicationDispense.setEntryData(row.getString("entry_data"));
            medicationDispense.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(medicationDispense);
        }

        return results;
    }

    public void add(MedicationDispense medicationDispense) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("medication_dispense_id", medicationDispense.getMedicationDispenseId())
                .addColumnUUID("patient_id", medicationDispense.getPatientId())
                .addColumnUUID("service_id", medicationDispense.getServiceId())
                .addColumnTimestamp("effective_datetime", medicationDispense.getEffectiveDateTime())
                .addColumnMap("meta_data", medicationDispense.getMetaData())
                .addColumnString("entry_data", medicationDispense.getEntryData())
                .addColumnTimestamp("last_updated", medicationDispense.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
