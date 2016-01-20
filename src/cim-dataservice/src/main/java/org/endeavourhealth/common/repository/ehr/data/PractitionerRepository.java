package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Practitioner;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PractitionerRepository extends Repository {
	private static final String TableName = "practitioner";

	public PractitionerRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Practitioner> getPractitionersByServicePractitionerId(UUID serviceId, UUID practitionerId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("service_id", serviceId))
                .and(QueryBuilder.eq("practitioner_id", practitionerId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Practitioner> results = new ArrayList<>();

        for (Row row : rows) {
            Practitioner practitioner = new Practitioner();
            practitioner.setPractitionerId(row.getUUID("practitioner_id"));
            practitioner.setServiceId(row.getUUID("service_id"));
            practitioner.setMetaData(row.getMap("meta_data", String.class, String.class));
            practitioner.setEntryData(row.getString("entry_data"));
            practitioner.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(practitioner);
        }

        return results;
    }

    public void add(Practitioner practitioner) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("practitioner_id", practitioner.getPractitionerId())
                .addColumnUUID("service_id", practitioner.getServiceId())
                .addColumnMap("meta_data", practitioner.getMetaData())
                .addColumnString("entry_data", practitioner.getEntryData())
                .addColumnTimestamp("last_updated", practitioner.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
