package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.ClinicalImpression;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClinicalImpressionRepository extends Repository {
	private static final String TableName = "clinical_impression";

	public ClinicalImpressionRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<ClinicalImpression> getClinicalImpressionsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<ClinicalImpression> results = new ArrayList<>();

        for (Row row : rows) {
            ClinicalImpression clinicalImpression = new ClinicalImpression();
            clinicalImpression.setClinicalImpressionId(row.getUUID("clinical_impression_id"));
            clinicalImpression.setPatientId(row.getUUID("patient_id"));
            clinicalImpression.setServiceId(row.getUUID("service_id"));
            clinicalImpression.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            clinicalImpression.setMetaData(row.getMap("meta_data", String.class, String.class));
            clinicalImpression.setEntryData(row.getString("entry_data"));
            clinicalImpression.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(clinicalImpression);
        }

        return results;
    }

    public void add(ClinicalImpression clinicalImpression) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("clinical_impression_id", clinicalImpression.getClinicalImpressionId())
                .addColumnUUID("patient_id", clinicalImpression.getPatientId())
                .addColumnUUID("service_id", clinicalImpression.getServiceId())
                .addColumnTimestamp("effective_datetime", clinicalImpression.getEffectiveDateTime())
                .addColumnMap("meta_data", clinicalImpression.getMetaData())
                .addColumnString("entry_data", clinicalImpression.getEntryData())
                .addColumnTimestamp("last_updated", clinicalImpression.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
