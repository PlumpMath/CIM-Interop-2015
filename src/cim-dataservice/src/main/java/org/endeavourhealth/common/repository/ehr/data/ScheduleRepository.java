package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Schedule;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScheduleRepository extends Repository {
	private static final String TableName = "schedule";

	public ScheduleRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Schedule> getSchedulesByScheduleId(UUID scheduleId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("schedule_id", scheduleId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Schedule> results = new ArrayList<>();

        for (Row row : rows) {
            Schedule schedule = new Schedule();
            schedule.setScheduleId(row.getUUID("schedule_id"));
            schedule.setMetaData(row.getMap("meta_data", String.class, String.class));
            schedule.setEntryData(row.getString("entry_data"));
            schedule.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(schedule);
        }

        return results;
    }

    public void add(Schedule schedule) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("schedule_id", schedule.getScheduleId())
                .addColumnMap("meta_data", schedule.getMetaData())
                .addColumnString("entry_data", schedule.getEntryData())
                .addColumnTimestamp("last_updated", schedule.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
