package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.NutritionOrder;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NutritionOrderRepository extends Repository {
	private static final String TableName = "nutrition_order";

	public NutritionOrderRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<NutritionOrder> getNutritionOrdersByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<NutritionOrder> results = new ArrayList<>();

        for (Row row : rows) {
            NutritionOrder nutritionOrder = new NutritionOrder();
            nutritionOrder.setNutritionOrderId(row.getUUID("nutrition_order_id"));
            nutritionOrder.setPatientId(row.getUUID("patient_id"));
            nutritionOrder.setServiceId(row.getUUID("service_id"));
            nutritionOrder.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            nutritionOrder.setMetaData(row.getMap("meta_data", String.class, String.class));
            nutritionOrder.setEntryData(row.getString("entry_data"));
            nutritionOrder.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(nutritionOrder);
        }

        return results;
    }

    public void add(NutritionOrder nutritionOrder) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("nutrition_order_id", nutritionOrder.getNutritionOrderId())
                .addColumnUUID("patient_id", nutritionOrder.getPatientId())
                .addColumnUUID("service_id", nutritionOrder.getServiceId())
                .addColumnTimestamp("effective_datetime", nutritionOrder.getEffectiveDateTime())
                .addColumnMap("meta_data", nutritionOrder.getMetaData())
                .addColumnString("entry_data", nutritionOrder.getEntryData())
                .addColumnTimestamp("last_updated", nutritionOrder.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
