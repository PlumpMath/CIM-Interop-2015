package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Orders;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrdersRepository extends Repository {
	private static final String TableName = "orders";

	public OrdersRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Orders> getOrdersByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Orders> results = new ArrayList<>();

        for (Row row : rows) {
            Orders orders = new Orders();
            orders.setOrderId(row.getUUID("order_id"));
            orders.setPatientId(row.getUUID("patient_id"));
            orders.setServiceId(row.getUUID("service_id"));
            orders.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            orders.setMetaData(row.getMap("meta_data", String.class, String.class));
            orders.setEntryData(row.getString("entry_data"));
            orders.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(orders);
        }

        return results;
    }

    public void add(Orders orders) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("order_id", orders.getOrderId())
                .addColumnUUID("patient_id", orders.getPatientId())
                .addColumnUUID("service_id", orders.getServiceId())
                .addColumnTimestamp("effective_datetime", orders.getEffectiveDateTime())
                .addColumnMap("meta_data", orders.getMetaData())
                .addColumnString("entry_data", orders.getEntryData())
                .addColumnTimestamp("last_updated", orders.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
