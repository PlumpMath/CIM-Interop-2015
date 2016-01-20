package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.HealthCareService;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HealthCareServiceRepository extends Repository {
	private static final String TableName = "health_care_service";

	public HealthCareServiceRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<HealthCareService> getHealthCareServicesByHealthCareServiceId(UUID healthCareServiceId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("health_care_service_id", healthCareServiceId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<HealthCareService> results = new ArrayList<>();

        for (Row row : rows) {
            HealthCareService healthCareService = new HealthCareService();
            healthCareService.setHealthCareServiceId(row.getUUID("health_care_service_id"));
            healthCareService.setMetaData(row.getMap("meta_data", String.class, String.class));
            healthCareService.setEntryData(row.getString("entry_data"));
            healthCareService.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(healthCareService);
        }

        return results;
    }

    public void add(HealthCareService healthCareService) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("health_care_service_id", healthCareService.getHealthCareServiceId())
                .addColumnMap("meta_data", healthCareService.getMetaData())
                .addColumnString("entry_data", healthCareService.getEntryData())
                .addColumnTimestamp("last_updated", healthCareService.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
