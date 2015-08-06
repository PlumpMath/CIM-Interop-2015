package org.endeavourhealth.cim.repository.informationSharing.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.repository.informationSharing.model.ServiceByOrganisation;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.*;
import org.endeavourhealth.cim.common.serializer.DeserializationException;
import org.endeavourhealth.cim.common.serializer.JsonSerializer;

public class ServiceByOrganisationRepository extends Repository {
	public ServiceByOrganisationRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public void add(ServiceByOrganisation serviceByOrganisation) throws RepositoryException {
		if (serviceByOrganisation == null)
			throw new IllegalArgumentException("Item to add is null");

		try {
			BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), "service_by_organisation")
					.addColumnString("id", serviceByOrganisation.getId())
					.addColumnString("schema_version", serviceByOrganisation.getSchemaVersion())
					.addColumnString("data", JsonSerializer.serialize(serviceByOrganisation))
					.build();

			getSession().execute(boundStatement);
		} catch (JsonProcessingException e) {
			throw new RepositoryException(e);
		}
	}

	public void update(ServiceByOrganisation serviceByOrganisation) throws RepositoryException {
		if (serviceByOrganisation == null)
			throw new IllegalArgumentException("ServiceByOrganisation to update is null");

		ServiceByOrganisation objectBeforeUpdate = getById(serviceByOrganisation.getId());

		if (objectBeforeUpdate == null)
			throw new RepositoryException("ServiceByOrganisation not found: " + serviceByOrganisation.getId().toString());

		if (!serviceByOrganisation.getId().equals(objectBeforeUpdate.getId()))
			throw new RepositoryException("ServiceByOrganisation ids do not match");

		try {
			Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

			BoundStatement boundStatement = new UpdateStatementBuilder(getStatementCache(), "service_by_organisation", clause)
					.addColumnString("schema_version", serviceByOrganisation.getSchemaVersion())
					.addColumnString("data", JsonSerializer.serialize(serviceByOrganisation))
					.addParameterString("id", serviceByOrganisation.getId())
					.build();

			getSession().execute(boundStatement);
		} catch (JsonProcessingException e) {
			throw new RepositoryException(e);
		}
	}

	public ServiceByOrganisation getById(String organisationId) throws RepositoryException {
		Row row = StringKeyRepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), "service_by_organisation", "id", organisationId);

		if (row == null)
			return null;

		try {
			return JsonSerializer.deserialize(ServiceByOrganisation.class, row.getString("data"));
		} catch (DeserializationException e) {
			throw new RepositoryException(e);
		}
	}
}
