package org.endeavourhealth.cim.repository.informationSharing.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.common.repository.common.data.*;
import org.endeavourhealth.cim.repository.informationSharing.model.AgreementByService;
import org.endeavourhealth.cim.common.repository.DataConfiguration;
import org.endeavourhealth.cim.common.serializer.DeserializationException;
import org.endeavourhealth.cim.common.serializer.JsonSerializer;

import java.util.UUID;

public class AgreementByServiceRepository extends Repository {
	private static final String TableName = "agreement_by_service";

	public AgreementByServiceRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public void add(AgreementByService agreementByService) throws RepositoryException {
		if (agreementByService == null)
			throw new IllegalArgumentException("Item to add is null");

		try {
			BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
					.addColumnUUID("id", agreementByService.getId())
					.addColumnString("schema_version", agreementByService.getSchemaVersion())
					.addColumnString("data", JsonSerializer.serialize(agreementByService))
					.build();

			getSession().execute(boundStatement);
		} catch (JsonProcessingException e) {
			throw new RepositoryException(e);
		}
	}

	public void update(AgreementByService agreementByService) throws RepositoryException {
		if (agreementByService == null)
			throw new IllegalArgumentException("Item to update is null");

		AgreementByService objectBeforeUpdate = getById(agreementByService.getId());

		if (objectBeforeUpdate == null)
			throw new RepositoryException("Item not found: " + agreementByService.getId().toString());

		if (!agreementByService.getId().equals(objectBeforeUpdate.getId()))
			throw new RepositoryException("Item ids do not match");

		try {
			Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

			BoundStatement boundStatement = new UpdateStatementBuilder(getStatementCache(), TableName, clause)
					.addColumnString("schema_version", agreementByService.getSchemaVersion())
					.addColumnString("data", JsonSerializer.serialize(agreementByService))
					.addParameterUUID("id", agreementByService.getId())
					.build();

			getSession().execute(boundStatement);
		} catch (JsonProcessingException e) {
			throw new RepositoryException(e);
		}
	}

	public AgreementByService getById(UUID serviceId) throws RepositoryException {
		Row row = RepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), TableName, "id", serviceId);

		if (row == null)
			return null;

		try {
			return JsonSerializer.deserialize(AgreementByService.class, row.getString("data"));
		} catch (DeserializationException e) {
			throw new RepositoryException(e);
		}
	}
}
