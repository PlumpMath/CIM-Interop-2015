package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Device;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceRepository extends Repository {
	private static final String TableName = "device";

	public DeviceRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Device> getDevicesByDeviceId(UUID deviceId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("device_id", deviceId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Device> results = new ArrayList<>();

        for (Row row : rows) {
            Device device = new Device();
            device.setDeviceId(row.getUUID("device_id"));
            device.setMetaData(row.getMap("meta_data", String.class, String.class));
            device.setEntryData(row.getString("entry_data"));
            device.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(device);
        }

        return results;
    }

    public void add(Device device) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("device_id", device.getDeviceId())
                .addColumnMap("meta_data", device.getMetaData())
                .addColumnString("entry_data", device.getEntryData())
                .addColumnTimestamp("last_updated", device.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
