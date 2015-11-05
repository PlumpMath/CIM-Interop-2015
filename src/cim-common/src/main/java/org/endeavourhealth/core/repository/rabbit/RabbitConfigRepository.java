package org.endeavourhealth.core.repository.rabbit;

import com.datastax.driver.core.Row;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.core.repository.common.data.StringKeyRepositoryHelper;
import org.endeavourhealth.core.text.TextUtils;

public class RabbitConfigRepository extends Repository {
	private static final String TableName = "rabbit_config";
	private static RabbitConfigRepository _instance;

	public static RabbitConfigRepository getInstance() {
		if (_instance == null)
			_instance = new RabbitConfigRepository();

		return _instance;
	}

	public static void setInstance(RabbitConfigRepository instance) {
		_instance = instance;
	}

	public RabbitConfigRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public RabbitConfig getByChannelName(String channelName) throws RepositoryException {
		Row row = StringKeyRepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), TableName, "channelName", channelName);

		if (TextUtils.isNullOrTrimmedEmpty(channelName))
			return null;

		if (row == null)
			return null;

		return populateUser(row);
	}

	private RabbitConfig populateUser(Row row) {
		RabbitConfig result = new RabbitConfig();
		result.setChannelName(row.getString("channelName"));
		result.setUsernamePassword(row.getString("usernamePassword"));
		result.setUri(row.getString("uri"));
		result.setDataSchemaVersion(row.getString("data_schema_version"));
		return result;
	}
}
