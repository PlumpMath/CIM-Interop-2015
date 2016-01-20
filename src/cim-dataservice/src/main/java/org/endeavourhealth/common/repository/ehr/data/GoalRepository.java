package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Goal;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GoalRepository extends Repository {
	private static final String TableName = "goal";

	public GoalRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Goal> getGoalsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Goal> results = new ArrayList<>();

        for (Row row : rows) {
            Goal goal = new Goal();
            goal.setGoalId(row.getUUID("goal_id"));
            goal.setPatientId(row.getUUID("patient_id"));
            goal.setServiceId(row.getUUID("service_id"));
            goal.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            goal.setMetaData(row.getMap("meta_data", String.class, String.class));
            goal.setEntryData(row.getString("entry_data"));
            goal.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(goal);
        }

        return results;
    }

    public void add(Goal goal) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("goal_id", goal.getGoalId())
                .addColumnUUID("patient_id", goal.getPatientId())
                .addColumnUUID("service_id", goal.getServiceId())
                .addColumnTimestamp("effective_datetime", goal.getEffectiveDateTime())
                .addColumnMap("meta_data", goal.getMetaData())
                .addColumnString("entry_data", goal.getEntryData())
                .addColumnTimestamp("last_updated", goal.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
