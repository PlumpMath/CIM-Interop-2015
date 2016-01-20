package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model. FamilyMemberHistory;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FamilyMemberHistoryRepository extends Repository {
	private static final String TableName = "family_member_history";

	public FamilyMemberHistoryRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<FamilyMemberHistory> getFamilyMemberHistorysByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<FamilyMemberHistory> results = new ArrayList<>();

        for (Row row : rows) {
             FamilyMemberHistory familyMemberHistory = new FamilyMemberHistory();
             familyMemberHistory.setFamilyMemberHistoryId(row.getUUID("family_member_history_id"));
             familyMemberHistory.setPatientId(row.getUUID("patient_id"));
             familyMemberHistory.setServiceId(row.getUUID("service_id"));
             familyMemberHistory.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
             familyMemberHistory.setMetaData(row.getMap("meta_data", String.class, String.class));
             familyMemberHistory.setEntryData(row.getString("entry_data"));
             familyMemberHistory.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(familyMemberHistory);
        }

        return results;
    }

    public void add(FamilyMemberHistory familyMemberHistory) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("family_member_history_id", familyMemberHistory.getFamilyMemberHistoryId())
                .addColumnUUID("patient_id",  familyMemberHistory.getPatientId())
                .addColumnUUID("service_id",  familyMemberHistory.getServiceId())
                .addColumnTimestamp("effective_datetime",  familyMemberHistory.getEffectiveDateTime())
                .addColumnMap("meta_data",  familyMemberHistory.getMetaData())
                .addColumnString("entry_data",  familyMemberHistory.getEntryData())
                .addColumnTimestamp("last_updated",  familyMemberHistory.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
