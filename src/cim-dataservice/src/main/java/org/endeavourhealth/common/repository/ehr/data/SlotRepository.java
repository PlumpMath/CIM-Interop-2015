package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Slot;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SlotRepository extends Repository {
	private static final String TableName = "slot";

	public SlotRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Slot> getSlotsByScheduleId(UUID scheduleId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("schedule_id", scheduleId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Slot> results = new ArrayList<>();

        for (Row row : rows) {
            Slot slot = new Slot();
            slot.setSlotId(row.getUUID("slot_id"));
            slot.setScheduleId(row.getUUID("schedule_id"));
            slot.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            slot.setMetaData(row.getMap("meta_data", String.class, String.class));
            slot.setEntryData(row.getString("entry_data"));
            slot.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(slot);
        }

        return results;
    }

    public void add(Slot slot) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("slot_id", slot.getSlotId())
                .addColumnUUID("schedule_id", slot.getScheduleId())
                .addColumnTimestamp("effective_datetime", slot.getEffectiveDateTime())
                .addColumnMap("meta_data", slot.getMetaData())
                .addColumnString("entry_data", slot.getEntryData())
                .addColumnTimestamp("last_updated", slot.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
