package org.endeavourhealth.cim.audit;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.InformationSharingFramework.model.AgreementByService;
import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.*;
import org.endeavourhealth.cim.common.serializer.DeserializationException;
import org.endeavourhealth.cim.common.serializer.JsonSerializer;

import java.util.Date;
import java.util.UUID;

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
