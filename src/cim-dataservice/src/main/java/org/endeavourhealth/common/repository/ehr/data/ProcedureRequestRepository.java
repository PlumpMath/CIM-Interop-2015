package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.ProcedureRequest;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcedureRequestRepository extends Repository {
	private static final String TableName = "procedure_request";

	public ProcedureRequestRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<ProcedureRequest> getProcedureRequestsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<ProcedureRequest> results = new ArrayList<>();

        for (Row row : rows) {
            ProcedureRequest procedureRequest = new ProcedureRequest();
            procedureRequest.setProcedureRequestId(row.getUUID("procedure_request_id"));
            procedureRequest.setPatientId(row.getUUID("patient_id"));
            procedureRequest.setServiceId(row.getUUID("service_id"));
            procedureRequest.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            procedureRequest.setMetaData(row.getMap("meta_data", String.class, String.class));
            procedureRequest.setEntryData(row.getString("entry_data"));
            procedureRequest.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(procedureRequest);
        }

        return results;
    }

    public void add(ProcedureRequest procedureRequest) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("procedure_request_id", procedureRequest.getProcedureRequestId())
                .addColumnUUID("patient_id", procedureRequest.getPatientId())
                .addColumnUUID("service_id", procedureRequest.getServiceId())
                .addColumnTimestamp("effective_datetime", procedureRequest.getEffectiveDateTime())
                .addColumnMap("meta_data", procedureRequest.getMetaData())
                .addColumnString("entry_data", procedureRequest.getEntryData())
                .addColumnTimestamp("last_updated", procedureRequest.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
