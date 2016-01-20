package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.BodySite;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BodySiteRepository extends Repository {
	private static final String TableName = "body_site";

	public BodySiteRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<BodySite> getBodySitesByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<BodySite> results = new ArrayList<>();

        for (Row row : rows) {
            BodySite bodySite = new BodySite();
            bodySite.setBodySiteId(row.getUUID("body_site_id"));
            bodySite.setPatientId(row.getUUID("patient_id"));
            bodySite.setServiceId(row.getUUID("service_id"));
            bodySite.setMetaData(row.getMap("meta_data", String.class, String.class));
            bodySite.setEntryData(row.getString("entry_data"));
            bodySite.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(bodySite);
        }

        return results;
    }

    public void add(BodySite bodySite) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("body_site_id", bodySite.getBodySiteId())
                .addColumnUUID("patient_id", bodySite.getPatientId())
                .addColumnUUID("service_id", bodySite.getServiceId())
                .addColumnMap("meta_data", bodySite.getMetaData())
                .addColumnString("entry_data", bodySite.getEntryData())
                .addColumnTimestamp("last_updated", bodySite.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
