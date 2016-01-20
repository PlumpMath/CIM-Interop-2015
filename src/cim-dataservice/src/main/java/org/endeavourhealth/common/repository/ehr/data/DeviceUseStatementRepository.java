package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.DeviceUseStatement;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceUseStatementRepository extends Repository {
	private static final String TableName = "device_use_statement";

	public DeviceUseStatementRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<DeviceUseStatement> getDeviceUseStatementsByDeviceId(UUID deviceId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("device_id", deviceId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<DeviceUseStatement> results = new ArrayList<>();

        for (Row row : rows) {
            DeviceUseStatement deviceUseStatement = new DeviceUseStatement();
            deviceUseStatement.setDeviceUseStatementId(row.getUUID("device_use_statement_id"));
            deviceUseStatement.setDeviceId(row.getUUID("device_id"));
            deviceUseStatement.setMetaData(row.getMap("meta_data", String.class, String.class));
            deviceUseStatement.setEntryData(row.getString("entry_data"));
            deviceUseStatement.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(deviceUseStatement);
        }

        return results;
    }

    public void add(DeviceUseStatement deviceUseStatement) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("device_use_statement_id", deviceUseStatement.getDeviceUseStatementId())
                .addColumnUUID("device_id", deviceUseStatement.getDeviceId())
                .addColumnMap("meta_data", deviceUseStatement.getMetaData())
                .addColumnString("entry_data", deviceUseStatement.getEntryData())
                .addColumnTimestamp("last_updated", deviceUseStatement.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
