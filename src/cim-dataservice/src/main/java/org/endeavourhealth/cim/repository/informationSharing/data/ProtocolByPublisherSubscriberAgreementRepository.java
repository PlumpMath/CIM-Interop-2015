package org.endeavourhealth.cim.repository.informationSharing.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.repository.informationSharing.model.ProtocolByPublisherSubscriberAgreement;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.*;
import org.endeavourhealth.cim.common.serializer.DeserializationException;
import org.endeavourhealth.cim.common.serializer.JsonSerializer;
import org.endeavourhealth.cim.common.stream.StreamExtension;

import java.util.UUID;

public class ProtocolByPublisherSubscriberAgreementRepository extends Repository {
	private static final String TableName = "protocol_by_publisher_and_subscriber_agreement";

	public ProtocolByPublisherSubscriberAgreementRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public void add(ProtocolByPublisherSubscriberAgreement item) throws RepositoryException {
		if (item == null)
			throw new IllegalArgumentException("Item to add is null");

		try {
			BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
					.addColumnUUID("publisherAgreementId", item.getPublisherAgreementId())
					.addColumnUUID("subscriberAgreementId", item.getSubscriberAgreementId())
					.addColumnString("schema_version", item.getSchemaVersion())
					.addColumnString("data", JsonSerializer.serialize(item))
					.build();

			getSession().execute(boundStatement);
		} catch (JsonProcessingException e) {
			throw new RepositoryException(e);
		}
	}

	public void update(ProtocolByPublisherSubscriberAgreement item) throws RepositoryException {
		if (item == null)
			throw new IllegalArgumentException("Item to update is null");

		ProtocolByPublisherSubscriberAgreement objectBeforeUpdate = getById(item.getPublisherAgreementId(), item.getSubscriberAgreementId());

		if (objectBeforeUpdate == null)
			throw new RepositoryException("Item not found: " + item.getPublisherAgreementId().toString() + ", " + item.getSubscriberAgreementId().toString());

		if (!item.getPublisherAgreementId().equals(objectBeforeUpdate.getPublisherAgreementId())
				&& !item.getSubscriberAgreementId().equals(objectBeforeUpdate.getSubscriberAgreementId()))
			throw new RepositoryException("Item ids do not match");

		try {
			Clause publisherClause = QueryBuilder.eq("publisherAgreementId", QueryBuilder.bindMarker("publisherAgreementId"));
			Clause subscriberClause = QueryBuilder.eq("subscriberAgreementId", QueryBuilder.bindMarker("subscriberAgreementId"));

			BoundStatement boundStatement = new UpdateStatementBuilder(getStatementCache(), TableName, new Clause[] {publisherClause, subscriberClause})
					.addColumnString("schema_version", item.getSchemaVersion())
					.addColumnString("data", JsonSerializer.serialize(item))
					.addParameterUUID("publisherAgreementId", item.getPublisherAgreementId())
					.addParameterUUID("subscriberAgreementId", item.getSubscriberAgreementId())
					.build();

			getSession().execute(boundStatement);
		} catch (JsonProcessingException e) {
			throw new RepositoryException(e);
		}
	}

	public ProtocolByPublisherSubscriberAgreement getById(UUID publisherAgreementId, UUID subscriberAgreementId) throws RepositoryException {
		PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
				.all()
				.from(TableName)
				.where(QueryBuilder.eq("publisherAgreementId", QueryBuilder.bindMarker("publisherAgreementId")))
				.and(QueryBuilder.eq("subscriberAgreementId", QueryBuilder.bindMarker("subscriberAgreementId"))));

		Row row = getSession().execute(preparedStatement
				.bind()
				.setUUID("publisherAgreementId", publisherAgreementId)
				.setUUID("subscriberAgreementId", subscriberAgreementId))
				.all()
				.stream()
				.collect(StreamExtension.singleOrNullCollector());

		if (row == null)
			return null;

		try {
			return JsonSerializer.deserialize(ProtocolByPublisherSubscriberAgreement.class, row.getString("data"));
		} catch (DeserializationException e) {
			throw new RepositoryException(e);
		}
	}
}
