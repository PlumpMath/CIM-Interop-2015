package org.endeavourhealth.cim.repository.audit;

import com.datastax.driver.core.BoundStatement;
import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.*;

import java.util.Date;

public class AuditRepository extends Repository {
	private static final String TableName = "audit";

	public AuditRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public void add(Date date, String message) throws RepositoryException {
		if (TextUtils.isNullOrTrimmedEmpty(message))
			return;

		BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
				.addColumnTimestamp("rowkey", date)
                .addColumnString("message", message)
                .build();

		getSession().execute(boundStatement);
	}
}
