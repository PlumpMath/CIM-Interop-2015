package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Medication;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicationRepository extends Repository {
	private static final String TableName = "medication";

	public MedicationRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Medication> getMedicationsByMedicationId(UUID medicationId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("medication_id", medicationId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Medication> results = new ArrayList<>();

        for (Row row : rows) {
            Medication medication = new Medication();
            medication.setMedicationId(row.getUUID("medication_id"));
            medication.setMetaData(row.getMap("meta_data", String.class, String.class));
            medication.setEntryData(row.getString("entry_data"));
            medication.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(medication);
        }

        return results;
    }

    public void add(Medication medication) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("medication_id", medication.getMedicationId())
                .addColumnMap("meta_data", medication.getMetaData())
                .addColumnString("entry_data", medication.getEntryData())
                .addColumnTimestamp("last_updated", medication.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
