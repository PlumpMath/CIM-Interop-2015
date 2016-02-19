package org.endeavourhealth.cim.repository.domains.audit;

import com.datastax.driver.core.BoundStatement;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.endeavourhealth.cim.repository.RepositoryConfiguration;
import org.endeavourhealth.cim.repository.framework.Repository;
import org.endeavourhealth.cim.repository.framework.InsertStatementBuilder;
import org.endeavourhealth.cim.repository.framework.RepositoryException;

import java.util.Date;
import java.util.UUID;

public class AuditRepository extends Repository
{

	private static final String TableName = "audit";

	public AuditRepository() {
		super(RepositoryConfiguration.DATASERVICE_KEYSPACE);
	}

	public void add(UUID auditId, Date date, String apikey, String direction, String email, String url, String status, String message) throws RepositoryException {

		BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
				.addColumnString("email", email)
				.addColumnTimestamp("datetime", date)
				.addColumnUUID("auditid", auditId)
				.addColumnString("direction", direction)
				.addColumnString("apikey", apikey)
				.addColumnString("url", url)
				.addColumnString("status", status)
				.addColumnString("message", message)
                .build();

		getSession().execute(boundStatement);
	}
}
