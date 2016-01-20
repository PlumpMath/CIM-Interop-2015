package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.ReferralRequest;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReferralRequestRepository extends Repository {
	private static final String TableName = "referral_request";

	public ReferralRequestRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<ReferralRequest> getReferralRequestsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<ReferralRequest> results = new ArrayList<>();

        for (Row row : rows) {
            ReferralRequest referralRequest = new ReferralRequest();
            referralRequest.setReferralRequestId(row.getUUID("referral_request_id"));
            referralRequest.setPatientId(row.getUUID("patient_id"));
            referralRequest.setServiceId(row.getUUID("service_id"));
            referralRequest.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            referralRequest.setMetaData(row.getMap("meta_data", String.class, String.class));
            referralRequest.setEntryData(row.getString("entry_data"));
            referralRequest.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(referralRequest);
        }

        return results;
    }

    public void add(ReferralRequest referralRequest) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("referral_request_id", referralRequest.getReferralRequestId())
                .addColumnUUID("patient_id", referralRequest.getPatientId())
                .addColumnUUID("service_id", referralRequest.getServiceId())
                .addColumnTimestamp("effective_datetime", referralRequest.getEffectiveDateTime())
                .addColumnMap("meta_data", referralRequest.getMetaData())
                .addColumnString("entry_data", referralRequest.getEntryData())
                .addColumnTimestamp("last_updated", referralRequest.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
