package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.MedicationOrder;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicationOrderRepository extends Repository {
	private static final String TableName = "medication_order";

	public MedicationOrderRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<MedicationOrder> getMedicationOrdersByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<MedicationOrder> results = new ArrayList<>();

        for (Row row : rows) {
            MedicationOrder medicationOrder = new MedicationOrder();
            medicationOrder.setMedicationOrderId(row.getUUID("medication_order_id"));
            medicationOrder.setPatientId(row.getUUID("patient_id"));
            medicationOrder.setServiceId(row.getUUID("service_id"));
            medicationOrder.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            medicationOrder.setMetaData(row.getMap("meta_data", String.class, String.class));
            medicationOrder.setEntryData(row.getString("entry_data"));
            medicationOrder.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(medicationOrder);
        }

        return results;
    }

    public void add(MedicationOrder medicationOrder) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("medication_order_id", medicationOrder.getMedicationOrderId())
                .addColumnUUID("patient_id", medicationOrder.getPatientId())
                .addColumnUUID("service_id", medicationOrder.getServiceId())
                .addColumnTimestamp("effective_datetime", medicationOrder.getEffectiveDateTime())
                .addColumnMap("meta_data", medicationOrder.getMetaData())
                .addColumnString("entry_data", medicationOrder.getEntryData())
                .addColumnTimestamp("last_updated", medicationOrder.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
