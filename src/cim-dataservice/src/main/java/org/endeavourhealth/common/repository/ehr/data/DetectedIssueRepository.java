package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.DetectedIssue;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetectedIssueRepository extends Repository {
	private static final String TableName = "detected_issue";

	public DetectedIssueRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<DetectedIssue> getDetectedIssuesByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<DetectedIssue> results = new ArrayList<>();

        for (Row row : rows) {
            DetectedIssue detectedIssue = new DetectedIssue();
            detectedIssue.setDetectedIssueId(row.getUUID("detected_issue_id"));
            detectedIssue.setPatientId(row.getUUID("patient_id"));
            detectedIssue.setServiceId(row.getUUID("service_id"));
            detectedIssue.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            detectedIssue.setMetaData(row.getMap("meta_data", String.class, String.class));
            detectedIssue.setEntryData(row.getString("entry_data"));
            detectedIssue.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(detectedIssue);
        }

        return results;
    }

    public void add(DetectedIssue detectedIssue) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("detected_issue_id", detectedIssue.getDetectedIssueId())
                .addColumnUUID("patient_id", detectedIssue.getPatientId())
                .addColumnUUID("service_id", detectedIssue.getServiceId())
                .addColumnTimestamp("effective_datetime", detectedIssue.getEffectiveDateTime())
                .addColumnMap("meta_data", detectedIssue.getMetaData())
                .addColumnString("entry_data", detectedIssue.getEntryData())
                .addColumnTimestamp("last_updated", detectedIssue.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
