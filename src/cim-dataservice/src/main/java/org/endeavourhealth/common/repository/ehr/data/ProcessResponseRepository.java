package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.ProcessResponse;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcessResponseRepository extends Repository {
	private static final String TableName = "process_response";

	public ProcessResponseRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<ProcessResponse> getProcessResponsesByProcessRequestId(UUID processRequestId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("process_request_id", processRequestId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<ProcessResponse> results = new ArrayList<>();

        for (Row row : rows) {
            ProcessResponse processResponse = new ProcessResponse();
            processResponse.setProcessResponseId(row.getUUID("process_response_id"));
            processResponse.setProcessRequestId(row.getUUID("process_request_id"));
            processResponse.setMetaData(row.getMap("meta_data", String.class, String.class));
            processResponse.setEntryData(row.getString("entry_data"));
            processResponse.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(processResponse);
        }

        return results;
    }

    public void add(ProcessResponse processResponse) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("process_response_id", processResponse.getProcessResponseId())
                .addColumnUUID("process_request_id", processResponse.getProcessRequestId())
                .addColumnMap("meta_data", processResponse.getMetaData())
                .addColumnString("entry_data", processResponse.getEntryData())
                .addColumnTimestamp("last_updated", processResponse.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
