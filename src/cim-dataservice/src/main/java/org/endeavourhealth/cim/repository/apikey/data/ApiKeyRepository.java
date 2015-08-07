package org.endeavourhealth.cim.repository.apikey.data;

import com.datastax.driver.core.Row;
import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.Repository;
import org.endeavourhealth.cim.common.data.RepositoryException;
import org.endeavourhealth.cim.common.data.StringKeyRepositoryHelper;
import org.endeavourhealth.cim.repository.apikey.model.ApiKey;

public class ApiKeyRepository extends Repository {
	private static final String TableName = "apikey";

	public ApiKeyRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public ApiKey getById(String apiKey) throws RepositoryException {
		Row row = StringKeyRepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), TableName, "apikey", apiKey);

		if (TextUtils.isNullOrTrimmedEmpty(apiKey))
			return null;

		if (row == null)
			return null;

		ApiKey result = new ApiKey();
		result.setApiKey(row.getString("apikey"));
		result.setSecret(row.getString("secret"));
		result.setUserdata(row.getString("userdata"));
		result.setUserDataSchemaVersion(row.getString("userdata_schema_version"));
		return result;
	}
}
