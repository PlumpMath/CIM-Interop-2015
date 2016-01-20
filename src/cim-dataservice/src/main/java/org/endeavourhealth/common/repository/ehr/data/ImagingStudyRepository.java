package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.ImagingStudy;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImagingStudyRepository extends Repository {
	private static final String TableName = "imaging_study";

	public ImagingStudyRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<ImagingStudy> getImagingStudysByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<ImagingStudy> results = new ArrayList<>();

        for (Row row : rows) {
            ImagingStudy imagingStudy = new ImagingStudy();
            imagingStudy.setImagingStudyId(row.getUUID("imaging_study_id"));
            imagingStudy.setPatientId(row.getUUID("patient_id"));
            imagingStudy.setServiceId(row.getUUID("service_id"));
            imagingStudy.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            imagingStudy.setMetaData(row.getMap("meta_data", String.class, String.class));
            imagingStudy.setEntryData(row.getString("entry_data"));
            imagingStudy.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(imagingStudy);
        }

        return results;
    }

    public void add(ImagingStudy imagingStudy) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("imaging_study_id", imagingStudy.getImagingStudyId())
                .addColumnUUID("patient_id", imagingStudy.getPatientId())
                .addColumnUUID("service_id", imagingStudy.getServiceId())
                .addColumnTimestamp("effective_datetime", imagingStudy.getEffectiveDateTime())
                .addColumnMap("meta_data", imagingStudy.getMetaData())
                .addColumnString("entry_data", imagingStudy.getEntryData())
                .addColumnTimestamp("last_updated", imagingStudy.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
