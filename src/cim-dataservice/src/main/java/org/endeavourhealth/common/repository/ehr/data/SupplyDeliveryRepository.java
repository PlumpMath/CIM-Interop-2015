package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.SupplyDelivery;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SupplyDeliveryRepository extends Repository {
	private static final String TableName = "supply_delivery";

	public SupplyDeliveryRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<SupplyDelivery> getSupplyDeliverysByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<SupplyDelivery> results = new ArrayList<>();

        for (Row row : rows) {
            SupplyDelivery supplyDelivery = new SupplyDelivery();
            supplyDelivery.setSupplyDeliveryId(row.getUUID("supply_delivery_id"));
            supplyDelivery.setPatientId(row.getUUID("patient_id"));
            supplyDelivery.setServiceId(row.getUUID("service_id"));
            supplyDelivery.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            supplyDelivery.setMetaData(row.getMap("meta_data", String.class, String.class));
            supplyDelivery.setEntryData(row.getString("entry_data"));
            supplyDelivery.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(supplyDelivery);
        }

        return results;
    }

    public void add(SupplyDelivery supplyDelivery) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("supply_delivery_id", supplyDelivery.getSupplyDeliveryId())
                .addColumnUUID("patient_id", supplyDelivery.getPatientId())
                .addColumnUUID("service_id", supplyDelivery.getServiceId())
                .addColumnTimestamp("effective_datetime", supplyDelivery.getEffectiveDateTime())
                .addColumnMap("meta_data", supplyDelivery.getMetaData())
                .addColumnString("entry_data", supplyDelivery.getEntryData())
                .addColumnTimestamp("last_updated", supplyDelivery.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
