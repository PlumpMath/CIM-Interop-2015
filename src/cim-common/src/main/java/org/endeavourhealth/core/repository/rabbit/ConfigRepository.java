package org.endeavourhealth.core.repository.rabbit;

import com.datastax.driver.core.Row;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.core.repository.common.data.StringKeyRepositoryHelper;
import org.endeavourhealth.core.serializer.DeserializationException;
import org.endeavourhealth.core.serializer.JsonSerializer;
import org.endeavourhealth.core.text.TextUtils;

public class ConfigRepository extends Repository {
	private static ConfigRepository _instance;

	public static ConfigRepository getInstance() {
		if (_instance == null)
			_instance = new ConfigRepository();

		return _instance;
	}

	public static void setInstance(ConfigRepository instance) {
		_instance = instance;
	}

	public ConfigRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public <T extends Object> T getConfigByName(String configName, Class c) throws RepositoryException, DeserializationException {
		if (TextUtils.isNullOrTrimmedEmpty(configName))
			return null;

		Row row = StringKeyRepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), "config", "configName", configName);

		if (row == null)
			return null;

		Object o = new JsonSerializer().deserialize(c, row.getString("configData"));
		return (T)o;
	}
}
