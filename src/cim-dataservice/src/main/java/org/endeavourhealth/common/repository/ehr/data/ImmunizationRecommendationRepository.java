package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.ImmunizationRecommendation;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImmunizationRecommendationRepository extends Repository {
	private static final String TableName = "immunization_recommendation";

	public ImmunizationRecommendationRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<ImmunizationRecommendation> getImmunizationRecommendationsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<ImmunizationRecommendation> results = new ArrayList<>();

        for (Row row : rows) {
            ImmunizationRecommendation immunizationRecommendation = new ImmunizationRecommendation();
            immunizationRecommendation.setImmunizationRecommendationId(row.getUUID("immunization_recommendation_id"));
            immunizationRecommendation.setPatientId(row.getUUID("patient_id"));
            immunizationRecommendation.setServiceId(row.getUUID("service_id"));
            immunizationRecommendation.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            immunizationRecommendation.setMetaData(row.getMap("meta_data", String.class, String.class));
            immunizationRecommendation.setEntryData(row.getString("entry_data"));
            immunizationRecommendation.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(immunizationRecommendation);
        }

        return results;
    }

    public void add(ImmunizationRecommendation immunizationRecommendation) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("immunization_recommendation_id", immunizationRecommendation.getImmunizationRecommendationId())
                .addColumnUUID("patient_id", immunizationRecommendation.getPatientId())
                .addColumnUUID("service_id", immunizationRecommendation.getServiceId())
                .addColumnTimestamp("effective_datetime", immunizationRecommendation.getEffectiveDateTime())
                .addColumnMap("meta_data", immunizationRecommendation.getMetaData())
                .addColumnString("entry_data", immunizationRecommendation.getEntryData())
                .addColumnTimestamp("last_updated", immunizationRecommendation.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
