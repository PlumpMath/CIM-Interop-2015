package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Communication;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommunicationRepository extends Repository {
	private static final String TableName = "communication";

	public CommunicationRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Communication> getCommunicationsBySenderId(UUID senderId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("sender_id", senderId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Communication> results = new ArrayList<>();

        for (Row row : rows) {
            Communication communication = new Communication();
            communication.setCommunicationId(row.getUUID("communication_id"));
            communication.setSenderId(row.getUUID("sender_id"));
            communication.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            communication.setMetaData(row.getMap("meta_data", String.class, String.class));
            communication.setEntryData(row.getString("entry_data"));
            communication.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(communication);
        }

        return results;
    }

    public void add(Communication communication) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("communication_id", communication.getCommunicationId())
                .addColumnUUID("sender_id", communication.getSenderId())
                .addColumnTimestamp("effective_datetime", communication.getEffectiveDateTime())
                .addColumnMap("meta_data", communication.getMetaData())
                .addColumnString("entry_data", communication.getEntryData())
                .addColumnTimestamp("last_updated", communication.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
