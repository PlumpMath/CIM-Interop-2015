package org.endeavourhealth.core.repository.common.data;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.core.repository.common.model.BaseEntity;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.model.AuditMode;
import org.endeavourhealth.core.serializer.DeserializationException;
import org.endeavourhealth.core.serializer.JsonSerializer;

import java.util.List;
import java.util.UUID;

public class GenericRepository<T extends BaseEntity> extends Repository {
	protected String className;
	protected Class<T> tClass;
	protected HelperForKeyAndBlobTables<T> lazyLoadedTableHelper;

	public GenericRepository(Class<T> tClass) {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
		className = tClass.getSimpleName().replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
		this.tClass = tClass;
	}

	public void add(T object, UUID userId) throws RepositoryException {
		if (object == null)
			throw new IllegalArgumentException("Item to add is null");

		try {
			// create logged batch
			BatchStatement batch = new BatchStatement();

			addInsertToBatch(batch, object);
			addAuditToBatch(batch, object, userId, AuditMode.add);

			getSession().execute(batch);
		} catch (JsonProcessingException e) {
			throw new RepositoryException(e);
		}
	}

	public void update(T object, UUID userId) throws RepositoryException {
		if (object == null)
			throw new IllegalArgumentException(className + " to update is null");

		T objectBeforeUpdate = getById(object.getId());

		if (objectBeforeUpdate == null)
			throw new RepositoryException(className + " not found: " + object.getId().toString());

		if (!object.getId().equals(objectBeforeUpdate.getId()))
			throw new RepositoryException(className + " ids do not match");

		try {
			// create logged batch
			BatchStatement batch = new BatchStatement();

			addUpdateToBatch(batch, object);
			addAuditToBatch(batch, object, userId, AuditMode.edit);

			getSession().execute(batch);
		} catch (JsonProcessingException e) {
			throw new RepositoryException(e);
		}
	}

	public T getById(UUID objectId) throws RepositoryException {
		return getTableHelper().getById(objectId);
	}

	public List<T> getMultipleById(List<UUID> objectIds) throws RepositoryException {
		return getTableHelper().getMultipleById(objectIds);
	}

	public List<T> getAll() throws RepositoryException {
		return getTableHelper().getAll();
	}

	protected void addInsertToBatch(BatchStatement batch, T object) throws JsonProcessingException {

		BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), className)
				.addColumnUUID("id", object.getId())
				.addColumnString("schema_version", object.getSchemaVersion())
				.addColumnString("data", JsonSerializer.serialize(object))
				.build();

		batch.add(boundStatement);
	}

	protected void addUpdateToBatch(BatchStatement batch, T object) throws JsonProcessingException {

		Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

		BoundStatement boundStatement = new UpdateStatementBuilder(getStatementCache(), className, clause)
				.addColumnString("schema_version", object.getSchemaVersion())
				.addColumnString("data", JsonSerializer.serialize(object))
				.addParameterUUID("id", object.getId())
				.build();

		batch.add(boundStatement);
	}

	protected HelperForKeyAndBlobTables<T> getTableHelper() {
		if (lazyLoadedTableHelper == null) {
			lazyLoadedTableHelper = new HelperForKeyAndBlobTables<>(
					getSession(),
					getStatementCache(),
					className,
					this::convert);
		}

		return lazyLoadedTableHelper;
	}

	protected T convert(String version, String data) throws RepositoryException {
		try {
			return JsonSerializer.deserialize(tClass, data);
		} catch (DeserializationException e) {
			throw new RepositoryException(e);
		}
	}

	protected void addAuditToBatch(BatchStatement batch, T object, UUID userId, AuditMode mode) throws JsonProcessingException {
		BoundStatement boundStatement = AuditHelper.buildAuditStatement(
				getStatementCache(),
				className,
				object.getId(),
				userId,
				mode,
				object.getSchemaVersion(),
				JsonSerializer.serialize(object));

		batch.add(boundStatement);
	}
}
