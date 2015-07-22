package org.endeavourhealth.cim.common.data;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.common.models.BaseEntity;
import org.endeavourhealth.cim.common.models.EntityIdentifier;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.models.AuditMode;
import org.endeavourhealth.cim.common.serializer.DeserializationException;
import org.endeavourhealth.cim.common.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GenericRepository<T extends BaseEntity> extends Repository {
	private String className;
	private Class<T> tClass;
	private HelperForKeyAndBlobTables<T> lazyLoadedTableHelper;
	private HelperForSearchTermTables lazyLoadedSearchTermTableHelper;

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
			insertSearchTerms(batch, object);
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
			updateSearchTerms(batch, objectBeforeUpdate, object);
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

	private void addInsertToBatch(BatchStatement batch, T object) throws JsonProcessingException {

		BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), className)
				.addColumnUUID("id", object.getId())
				.addColumnString("schema_version", object.getSchemaVersion())
				.addColumnString("data", JsonSerializer.serialize(object))
				.build();

		batch.add(boundStatement);
	}

	private void addUpdateToBatch(BatchStatement batch, T object) throws JsonProcessingException {

		Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

		BoundStatement boundStatement = new UpdateStatementBuilder(getStatementCache(), className, clause)
				.addColumnString("schema_version", object.getSchemaVersion())
				.addColumnString("data", JsonSerializer.serialize(object))
				.addParameterUUID("id", object.getId())
				.build();

		batch.add(boundStatement);
	}

	private void insertSearchTerms(BatchStatement batch, T object) {
		List<SearchPair> searchPairs = getSearchTerms(object);

		getSearchTermTableHelper().insert(
				batch,
				object.getId(),
				searchPairs);
	}

	private void updateSearchTerms(BatchStatement batch, T object, T updatedObject) {
		if (object.getId() != updatedObject.getId())
			throw new IllegalArgumentException("IDs do not match");

		List<SearchPair> currentIdentifiers = getSearchTerms(object);
		List<SearchPair> updatedIdentifiers = getSearchTerms(updatedObject);

		getSearchTermTableHelper().updateSearchTerms(
				batch,
				object.getId(),
				currentIdentifiers,
				updatedIdentifiers);
	}

	private List<SearchPair> getSearchTerms(T object) {

		List<SearchPair> pairs = new ArrayList<>();

		if (object.getIdentifiers() != null) {
			for (EntityIdentifier id : object.getIdentifiers()) {
				SearchPair pair = new SearchPair(id.getType().name(), id.getValue());
				pairs.add(pair);
			}
		}

		List<SearchPair> splitName = getSearchTermTableHelper().splitText("name", object.getName());
		pairs.addAll(splitName);

		return pairs;
	}

	private HelperForKeyAndBlobTables<T> getTableHelper() {
		if (lazyLoadedTableHelper == null) {
			lazyLoadedTableHelper = new HelperForKeyAndBlobTables<>(
					getSession(),
					getStatementCache(),
					className,
					this::convert);
		}

		return lazyLoadedTableHelper;
	}

	private HelperForSearchTermTables getSearchTermTableHelper() {
		if (lazyLoadedSearchTermTableHelper == null) {
			lazyLoadedSearchTermTableHelper = new HelperForSearchTermTables(
					getSession(),
					getStatementCache(),
					className +"_search_terms",
					"id");
		}

		return lazyLoadedSearchTermTableHelper;
	}

	private T convert(String version, String data) throws RepositoryException {
		try {
			return JsonSerializer.deserialize(tClass, data);
		} catch (DeserializationException e) {
			throw new RepositoryException(e);
		}
	}

	public List<T> searchByName(List<String> searchTerms) throws RepositoryException {
		List<UUID> ids = getSearchTermTableHelper().search("name", searchTerms);
		return getMultipleById(ids);
	}

	private void addAuditToBatch(BatchStatement batch, T object, UUID userId, AuditMode mode) throws JsonProcessingException {
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
