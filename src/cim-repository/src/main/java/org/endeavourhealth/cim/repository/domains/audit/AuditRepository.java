package org.endeavourhealth.cim.repository.domains.audit;

import com.datastax.driver.core.BoundStatement;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.endeavourhealth.cim.repository.RepositoryConfiguration;
import org.endeavourhealth.cim.repository.framework.Repository;
import org.endeavourhealth.cim.repository.framework.InsertStatementBuilder;
import org.endeavourhealth.cim.repository.framework.RepositoryException;

import java.util.Date;

public class AuditRepository extends Repository
{

	private static final String TableName = "audit";

	public AuditRepository() {
		super(RepositoryConfiguration.DATASERVICE_KEYSPACE);
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
