package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.DiagnosticOrder;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DiagnosticOrderRepository extends Repository {
	private static final String TableName = "diagnostic_order";

	public DiagnosticOrderRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<DiagnosticOrder> getDiagnosticOrdersByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<DiagnosticOrder> results = new ArrayList<>();

        for (Row row : rows) {
            DiagnosticOrder diagnosticOrder = new DiagnosticOrder();
            diagnosticOrder.setDiagnosticOrderId(row.getUUID("diagnostic_order_id"));
            diagnosticOrder.setPatientId(row.getUUID("patient_id"));
            diagnosticOrder.setServiceId(row.getUUID("service_id"));
            diagnosticOrder.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            diagnosticOrder.setMetaData(row.getMap("meta_data", String.class, String.class));
            diagnosticOrder.setEntryData(row.getString("entry_data"));
            diagnosticOrder.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(diagnosticOrder);
        }

        return results;
    }

    public void add(DiagnosticOrder diagnosticOrder) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("diagnostic_order_id", diagnosticOrder.getDiagnosticOrderId())
                .addColumnUUID("patient_id", diagnosticOrder.getPatientId())
                .addColumnUUID("service_id", diagnosticOrder.getServiceId())
                .addColumnTimestamp("effective_datetime", diagnosticOrder.getEffectiveDateTime())
                .addColumnMap("meta_data", diagnosticOrder.getMetaData())
                .addColumnString("entry_data", diagnosticOrder.getEntryData())
                .addColumnTimestamp("last_updated", diagnosticOrder.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
