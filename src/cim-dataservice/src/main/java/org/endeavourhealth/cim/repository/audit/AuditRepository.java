package org.endeavourhealth.cim.repository.audit;

import com.datastax.driver.core.BoundStatement;
import org.endeavourhealth.cim.common.text.TextUtils;
import org.endeavourhealth.cim.common.repository.DataConfiguration;
import org.endeavourhealth.cim.common.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.cim.common.repository.common.data.Repository;
import org.endeavourhealth.cim.common.repository.common.data.RepositoryException;

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
