package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Group;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupRepository extends Repository {
	private static final String TableName = "group";

	public GroupRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Group> getGroupsByGroupId(UUID groupId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("group_id", groupId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Group> results = new ArrayList<>();

        for (Row row : rows) {
            Group group = new Group();
            group.setGroupId(row.getUUID("group_id"));
            group.setMetaData(row.getMap("meta_data", String.class, String.class));
            group.setEntryData(row.getString("entry_data"));
            group.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(group);
        }

        return results;
    }

    public void add(Group group) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("group_id", group.getGroupId())
                .addColumnMap("meta_data", group.getMetaData())
                .addColumnString("entry_data", group.getEntryData())
                .addColumnTimestamp("last_updated", group.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
