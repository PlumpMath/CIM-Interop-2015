package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.ProcessRequest;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcessRequestRepository extends Repository {
	private static final String TableName = "process_request";

	public ProcessRequestRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<ProcessRequest> getProcessRequestsByProcessRequestId(UUID processRequestId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("process_request_id", processRequestId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<ProcessRequest> results = new ArrayList<>();

        for (Row row : rows) {
            ProcessRequest processRequest = new ProcessRequest();
            processRequest.setProcessRequestId(row.getUUID("process_request_id"));
            processRequest.setMetaData(row.getMap("meta_data", String.class, String.class));
            processRequest.setEntryData(row.getString("entry_data"));
            processRequest.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(processRequest);
        }

        return results;
    }

    public void add(ProcessRequest processRequest) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("process_request_id", processRequest.getProcessRequestId())
                .addColumnMap("meta_data", processRequest.getMetaData())
                .addColumnString("entry_data", processRequest.getEntryData())
                .addColumnTimestamp("last_updated", processRequest.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
