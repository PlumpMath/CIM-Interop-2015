package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Location;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocationRepository extends Repository {
	private static final String TableName = "location";

	public LocationRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Location> getLocationsByLocationId(UUID locationId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("location_id", locationId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Location> results = new ArrayList<>();

        for (Row row : rows) {
            Location location = new Location();
            location.setLocationId(row.getUUID("location_id"));
            location.setMetaData(row.getMap("meta_data", String.class, String.class));
            location.setEntryData(row.getString("entry_data"));
            location.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(location);
        }

        return results;
    }

    public void add(Location location) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("location_id", location.getLocationId())
                .addColumnMap("meta_data", location.getMetaData())
                .addColumnString("entry_data", location.getEntryData())
                .addColumnTimestamp("last_updated", location.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
