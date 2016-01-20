package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.MedicationAdministration;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicationAdministrationRepository extends Repository {
	private static final String TableName = "medication_administration";

	public MedicationAdministrationRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<MedicationAdministration> getMedicationAdministrationsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<MedicationAdministration> results = new ArrayList<>();

        for (Row row : rows) {
            MedicationAdministration medicationAdministration = new MedicationAdministration();
            medicationAdministration.setMedicationAdministrationId(row.getUUID("medication_administration_id"));
            medicationAdministration.setPatientId(row.getUUID("patient_id"));
            medicationAdministration.setServiceId(row.getUUID("service_id"));
            medicationAdministration.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            medicationAdministration.setMetaData(row.getMap("meta_data", String.class, String.class));
            medicationAdministration.setEntryData(row.getString("entry_data"));
            medicationAdministration.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(medicationAdministration);
        }

        return results;
    }

    public void add(MedicationAdministration medicationAdministration) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("medication_administration_id", medicationAdministration.getMedicationAdministrationId())
                .addColumnUUID("patient_id", medicationAdministration.getPatientId())
                .addColumnUUID("service_id", medicationAdministration.getServiceId())
                .addColumnTimestamp("effective_datetime", medicationAdministration.getEffectiveDateTime())
                .addColumnMap("meta_data", medicationAdministration.getMetaData())
                .addColumnString("entry_data", medicationAdministration.getEntryData())
                .addColumnTimestamp("last_updated", medicationAdministration.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
