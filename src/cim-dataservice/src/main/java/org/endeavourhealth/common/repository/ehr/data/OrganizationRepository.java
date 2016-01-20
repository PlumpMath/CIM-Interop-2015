package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Organization;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrganizationRepository extends Repository {
	private static final String TableName = "organization";

	public OrganizationRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Organization> getOrganizationsByOrganizationId(UUID organizationId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("organization_id", organizationId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Organization> results = new ArrayList<>();

        for (Row row : rows) {
            Organization organization = new Organization();
            organization.setOrganizationId(row.getUUID("organization_id"));
            organization.setMetaData(row.getMap("meta_data", String.class, String.class));
            organization.setEntryData(row.getString("entry_data"));
            organization.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(organization);
        }

        return results;
    }

    public void add(Organization organization) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("organization_id", organization.getOrganizationId())
                .addColumnMap("meta_data", organization.getMetaData())
                .addColumnString("entry_data", organization.getEntryData())
                .addColumnTimestamp("last_updated", organization.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
