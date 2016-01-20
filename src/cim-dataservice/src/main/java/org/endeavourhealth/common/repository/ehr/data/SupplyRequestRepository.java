package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.SupplyRequest;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SupplyRequestRepository extends Repository {
	private static final String TableName = "supply_request";

	public SupplyRequestRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<SupplyRequest> getSupplyRequestsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<SupplyRequest> results = new ArrayList<>();

        for (Row row : rows) {
            SupplyRequest supplyRequest = new SupplyRequest();
            supplyRequest.setSupplyRequestId(row.getUUID("supply_request_id"));
            supplyRequest.setPatientId(row.getUUID("patient_id"));
            supplyRequest.setServiceId(row.getUUID("service_id"));
            supplyRequest.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            supplyRequest.setMetaData(row.getMap("meta_data", String.class, String.class));
            supplyRequest.setEntryData(row.getString("entry_data"));
            supplyRequest.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(supplyRequest);
        }

        return results;
    }

    public void add(SupplyRequest supplyRequest) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("supply_request_id", supplyRequest.getSupplyRequestId())
                .addColumnUUID("patient_id", supplyRequest.getPatientId())
                .addColumnUUID("service_id", supplyRequest.getServiceId())
                .addColumnTimestamp("effective_datetime", supplyRequest.getEffectiveDateTime())
                .addColumnMap("meta_data", supplyRequest.getMetaData())
                .addColumnString("entry_data", supplyRequest.getEntryData())
                .addColumnTimestamp("last_updated", supplyRequest.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
