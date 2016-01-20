package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.DeviceComponent;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceComponentRepository extends Repository {
	private static final String TableName = "device_component";

	public DeviceComponentRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<DeviceComponent> getDeviceComponentsByDeviceComponentId(UUID deviceComponentId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("device_component_id", deviceComponentId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<DeviceComponent> results = new ArrayList<>();

        for (Row row : rows) {
            DeviceComponent deviceComponent = new DeviceComponent();
            deviceComponent.setDeviceComponentId(row.getUUID("device_component_id"));
            deviceComponent.setMetaData(row.getMap("meta_data", String.class, String.class));
            deviceComponent.setEntryData(row.getString("entry_data"));
            deviceComponent.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(deviceComponent);
        }

        return results;
    }

    public void add(DeviceComponent deviceComponent) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("device_component_id", deviceComponent.getDeviceComponentId())
                .addColumnMap("meta_data", deviceComponent.getMetaData())
                .addColumnString("entry_data", deviceComponent.getEntryData())
                .addColumnTimestamp("last_updated", deviceComponent.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
