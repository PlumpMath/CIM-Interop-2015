package org.endeavourhealth.cim.repository.apikey;

import com.datastax.driver.core.BoundStatement;
import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.InsertStatementBuilder;
import org.endeavourhealth.cim.common.data.Repository;
import org.endeavourhealth.cim.common.data.RepositoryException;

import java.util.Date;

public class ApiKeyRepository extends Repository {
	private static final String TableName = "apikey";

	public ApiKeyRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}


}
