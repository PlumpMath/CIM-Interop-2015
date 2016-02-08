package org.endeavourhealth.core.repository.audit;

import com.datastax.driver.core.BoundStatement;
import org.endeavourhealth.core.utils.TextUtils;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.Date;

public class AuditRepository extends Repository {

	private static final String TableName = "audit";

	public AuditRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public void add(Date date, String apikey, String message) throws RepositoryException {

		if (TextUtils.isNullOrTrimmedEmpty(message))
			return;

		BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
				.addColumnTimestamp("rowkey", date)
				.addColumnString("apikey", apikey)
                .addColumnString("message", message)
                .build();

		getSession().execute(boundStatement);
	}
}
