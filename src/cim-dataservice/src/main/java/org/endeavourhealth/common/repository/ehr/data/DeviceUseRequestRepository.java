package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.DeviceUseRequest;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceUseRequestRepository extends Repository {
	private static final String TableName = "device_use_request";

	public DeviceUseRequestRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<DeviceUseRequest> getDeviceUseRequestsByDeviceId(UUID deviceId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("device_id", deviceId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<DeviceUseRequest> results = new ArrayList<>();

        for (Row row : rows) {
            DeviceUseRequest deviceUseRequest = new DeviceUseRequest();
            deviceUseRequest.setDeviceUseRequestId(row.getUUID("device_use_request_id"));
            deviceUseRequest.setDeviceId(row.getUUID("device_id"));
            deviceUseRequest.setMetaData(row.getMap("meta_data", String.class, String.class));
            deviceUseRequest.setEntryData(row.getString("entry_data"));
            deviceUseRequest.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(deviceUseRequest);
        }

        return results;
    }

    public void add(DeviceUseRequest deviceUseRequest) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("device_use_request_id", deviceUseRequest.getDeviceUseRequestId())
                .addColumnUUID("device_id", deviceUseRequest.getDeviceId())
                .addColumnMap("meta_data", deviceUseRequest.getMetaData())
                .addColumnString("entry_data", deviceUseRequest.getEntryData())
                .addColumnTimestamp("last_updated", deviceUseRequest.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
