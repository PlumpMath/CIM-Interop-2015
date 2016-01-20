package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Flag;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlagRepository extends Repository {
	private static final String TableName = "flag";

	public FlagRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Flag> getFlagsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Flag> results = new ArrayList<>();

        for (Row row : rows) {
            Flag flag = new Flag();
            flag.setFlagId(row.getUUID("flag_id"));
            flag.setPatientId(row.getUUID("patient_id"));
            flag.setServiceId(row.getUUID("service_id"));
            flag.setMetaData(row.getMap("meta_data", String.class, String.class));
            flag.setEntryData(row.getString("entry_data"));
            flag.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(flag);
        }

        return results;
    }

    public void add(Flag flag) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("flag_id", flag.getFlagId())
                .addColumnUUID("patient_id", flag.getPatientId())
                .addColumnUUID("service_id", flag.getServiceId())
                .addColumnMap("meta_data", flag.getMetaData())
                .addColumnString("entry_data", flag.getEntryData())
                .addColumnTimestamp("last_updated", flag.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
