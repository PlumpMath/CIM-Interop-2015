package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.CommunicationRequest;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommunicationRequestRepository extends Repository {
	private static final String TableName = "communication_request";

	public CommunicationRequestRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<CommunicationRequest> getCommunicationRequestsBySenderId(UUID senderId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("sender_id", senderId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<CommunicationRequest> results = new ArrayList<>();

        for (Row row : rows) {
            CommunicationRequest communicationRequest = new CommunicationRequest();
            communicationRequest.setCommunicationRequestId(row.getUUID("communication_request_id"));
            communicationRequest.setSenderId(row.getUUID("sender_id"));
            communicationRequest.setDateRequested(row.getTimestamp("date_requested"));
            communicationRequest.setMetaData(row.getMap("meta_data", String.class, String.class));
            communicationRequest.setEntryData(row.getString("entry_data"));
            communicationRequest.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(communicationRequest);
        }

        return results;
    }

    public void add(CommunicationRequest communicationRequest) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("communication_request_id", communicationRequest.getCommunicationRequestId())
                .addColumnUUID("sender_id", communicationRequest.getSenderId())
                .addColumnTimestamp("date_requested", communicationRequest.getDateRequested())
                .addColumnMap("meta_data", communicationRequest.getMetaData())
                .addColumnString("entry_data", communicationRequest.getEntryData())
                .addColumnTimestamp("last_updated", communicationRequest.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
