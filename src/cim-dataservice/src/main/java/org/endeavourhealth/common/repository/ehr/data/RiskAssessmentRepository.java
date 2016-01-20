package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.RiskAssessment;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RiskAssessmentRepository extends Repository {
	private static final String TableName = "risk_assessment";

	public RiskAssessmentRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<RiskAssessment> getRiskAssessmentsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<RiskAssessment> results = new ArrayList<>();

        for (Row row : rows) {
            RiskAssessment riskAssessment = new RiskAssessment();
            riskAssessment.setRiskAssessmentId(row.getUUID("risk_assessment_id"));
            riskAssessment.setPatientId(row.getUUID("patient_id"));
            riskAssessment.setServiceId(row.getUUID("service_id"));
            riskAssessment.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            riskAssessment.setMetaData(row.getMap("meta_data", String.class, String.class));
            riskAssessment.setEntryData(row.getString("entry_data"));
            riskAssessment.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(riskAssessment);
        }

        return results;
    }

    public void add(RiskAssessment riskAssessment) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("risk_assessment_id", riskAssessment.getRiskAssessmentId())
                .addColumnUUID("patient_id", riskAssessment.getPatientId())
                .addColumnUUID("service_id", riskAssessment.getServiceId())
                .addColumnTimestamp("effective_datetime", riskAssessment.getEffectiveDateTime())
                .addColumnMap("meta_data", riskAssessment.getMetaData())
                .addColumnString("entry_data", riskAssessment.getEntryData())
                .addColumnTimestamp("last_updated", riskAssessment.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
