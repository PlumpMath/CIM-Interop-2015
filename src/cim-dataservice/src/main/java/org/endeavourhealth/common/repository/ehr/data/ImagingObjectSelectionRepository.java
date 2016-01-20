package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.ImagingObjectSelection;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImagingObjectSelectionRepository extends Repository {
	private static final String TableName = "imaging_object_selection";

	public ImagingObjectSelectionRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<ImagingObjectSelection> getImagingObjectSelectionsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<ImagingObjectSelection> results = new ArrayList<>();

        for (Row row : rows) {
            ImagingObjectSelection imagingObjectSelection = new ImagingObjectSelection();
            imagingObjectSelection.setImagingObjectSelectionId(row.getUUID("imaging_object_selection_id"));
            imagingObjectSelection.setPatientId(row.getUUID("patient_id"));
            imagingObjectSelection.setServiceId(row.getUUID("service_id"));
            imagingObjectSelection.setMetaData(row.getMap("meta_data", String.class, String.class));
            imagingObjectSelection.setEntryData(row.getString("entry_data"));
            imagingObjectSelection.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(imagingObjectSelection);
        }

        return results;
    }

    public void add(ImagingObjectSelection imagingObjectSelection) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("imaging_object_selection_id", imagingObjectSelection.getImagingObjectSelectionId())
                .addColumnUUID("patient_id", imagingObjectSelection.getPatientId())
                .addColumnUUID("service_id", imagingObjectSelection.getServiceId())
                .addColumnMap("meta_data", imagingObjectSelection.getMetaData())
                .addColumnString("entry_data", imagingObjectSelection.getEntryData())
                .addColumnTimestamp("last_updated", imagingObjectSelection.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
