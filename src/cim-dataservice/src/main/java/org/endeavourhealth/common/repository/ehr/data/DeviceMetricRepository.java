package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.DeviceMetric;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceMetricRepository extends Repository {
	private static final String TableName = "device_metric";

	public DeviceMetricRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<DeviceMetric> getDeviceMetricsByDeviceMetricId(UUID deviceMetricId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("device_metric_id", deviceMetricId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<DeviceMetric> results = new ArrayList<>();

        for (Row row : rows) {
            DeviceMetric deviceMetric = new DeviceMetric();
            deviceMetric.setDeviceMetricId(row.getUUID("device_metric_id"));
            deviceMetric.setMetaData(row.getMap("meta_data", String.class, String.class));
            deviceMetric.setEntryData(row.getString("entry_data"));
            deviceMetric.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(deviceMetric);
        }

        return results;
    }

    public void add(DeviceMetric deviceMetric) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("device_metric_id", deviceMetric.getDeviceMetricId())
                .addColumnMap("meta_data", deviceMetric.getMetaData())
                .addColumnString("entry_data", deviceMetric.getEntryData())
                .addColumnTimestamp("last_updated", deviceMetric.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
