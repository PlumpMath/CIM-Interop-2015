package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.MedicationStatement;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicationStatementRepository extends Repository {
	private static final String TableName = "medication_statement";

	public MedicationStatementRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<MedicationStatement> getMedicationStatementsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<MedicationStatement> results = new ArrayList<>();

        for (Row row : rows) {
            MedicationStatement medicationStatement = new MedicationStatement();
            medicationStatement.setMedicationStatementId(row.getUUID("medication_statement_id"));
            medicationStatement.setPatientId(row.getUUID("patient_id"));
            medicationStatement.setServiceId(row.getUUID("service_id"));
            medicationStatement.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            medicationStatement.setMetaData(row.getMap("meta_data", String.class, String.class));
            medicationStatement.setEntryData(row.getString("entry_data"));
            medicationStatement.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(medicationStatement);
        }

        return results;
    }

    public void add(MedicationStatement medicationStatement) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("medication_statement_id", medicationStatement.getMedicationStatementId())
                .addColumnUUID("patient_id", medicationStatement.getPatientId())
                .addColumnUUID("service_id", medicationStatement.getServiceId())
                .addColumnTimestamp("effective_datetime", medicationStatement.getEffectiveDateTime())
                .addColumnMap("meta_data", medicationStatement.getMetaData())
                .addColumnString("entry_data", medicationStatement.getEntryData())
                .addColumnTimestamp("last_updated", medicationStatement.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
