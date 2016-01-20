package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.OrderResponse;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderResponseRepository extends Repository {
	private static final String TableName = "order_response";

	public OrderResponseRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<OrderResponse> getOrderResponsesByOrderId(UUID orderId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("order_id", orderId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<OrderResponse> results = new ArrayList<>();

        for (Row row : rows) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderResponseId(row.getUUID("order_response_id"));
            orderResponse.setOrderId(row.getUUID("order_id"));
            orderResponse.setMetaData(row.getMap("meta_data", String.class, String.class));
            orderResponse.setEntryData(row.getString("entry_data"));
            orderResponse.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(orderResponse);
        }

        return results;
    }

    public void add(OrderResponse orderResponse) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("order_response_id", orderResponse.getOrderResponseId())
                .addColumnUUID("order_id", orderResponse.getOrderId())
                .addColumnMap("meta_data", orderResponse.getMetaData())
                .addColumnString("entry_data", orderResponse.getEntryData())
                .addColumnTimestamp("last_updated", orderResponse.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
