package org.endeavourhealth.cim.common.repository.common.data;

import com.datastax.driver.core.BatchStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.common.repository.common.model.AuditMode;
import org.endeavourhealth.cim.common.repository.common.model.BaseSearchableEntity;
import org.endeavourhealth.cim.common.repository.common.model.EntityIdentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class GenericSearchableRepository<T extends BaseSearchableEntity> extends GenericRepository {
	private String className;
	private Class<T> tClass;
	private HelperForKeyAndBlobTables<T> lazyLoadedTableHelper;
	private HelperForSearchTermTables lazyLoadedSearchTermTableHelper;

	public GenericSearchableRepository(Class<T> tClass) {
		super(tClass);
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

		T objectBeforeUpdate = (T)getById(object.getId());

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

	public List<T> searchByName(List<String> searchTerms) throws RepositoryException {
		List<UUID> ids = getSearchTermTableHelper().search("name", searchTerms);
		return getMultipleById(ids);
	}
}
