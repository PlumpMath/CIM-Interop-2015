package org.endeavourhealth.core.repository.user;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.core.repository.common.data.StringKeyRepositoryHelper;
import org.endeavourhealth.core.utils.TextUtils;

public class UserRepository extends Repository {
	private static final String TableName = "user";
	private static UserRepository _instance;

	public static UserRepository getInstance() {
		if (_instance == null)
			_instance = new UserRepository();

		return _instance;
	}

	public static void setInstance(UserRepository instance) {
		_instance = instance;
	}

	public UserRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public User getByApiKey(String apiKey) throws RepositoryException {
		Row row = StringKeyRepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), TableName, "apikey", apiKey);

		if (TextUtils.isNullOrTrimmedEmpty(apiKey))
			return null;

		if (row == null)
			return null;

		return populateUser(row);
	}

	public User getByEmail(String email) throws RepositoryException {
		Row row = StringKeyRepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), TableName, "email", email);

		if (TextUtils.isNullOrTrimmedEmpty(email))
			return null;

		if (row == null)
			return null;

		return populateUser(row);
	}

	private User populateUser(Row row) {
		User result = new User();
		result.setApiKey(row.getString("apikey"));
		result.setSecret(row.getString("secret"));
		result.setEmail(row.getString("email"));
		result.setData(row.getString("data"));
		result.setDataSchemaVersion(row.getString("data_schema_version"));
		return result;
	}

	public void add(String apikey, String secret, String email, String data, String dataSchemaVersion) throws RepositoryException {

		BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
				.addColumnString("apikey", apikey)
				.addColumnString("secret", secret)
				.addColumnString("email", email)
				.addColumnString("data", data)
				.addColumnString("data_schema_version", dataSchemaVersion)
				.build();

		getSession().execute(boundStatement);
	}
}
