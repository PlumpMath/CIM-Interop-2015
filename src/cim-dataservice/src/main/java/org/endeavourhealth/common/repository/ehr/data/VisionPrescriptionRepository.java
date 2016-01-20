package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.VisionPrescription;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VisionPrescriptionRepository extends Repository {
	private static final String TableName = "vision_prescription";

	public VisionPrescriptionRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<VisionPrescription> getVisionPrescriptionsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<VisionPrescription> results = new ArrayList<>();

        for (Row row : rows) {
            VisionPrescription visionPrescription = new VisionPrescription();
            visionPrescription.setVisionPrescriptionId(row.getUUID("vision_prescription_id"));
            visionPrescription.setPatientId(row.getUUID("patient_id"));
            visionPrescription.setServiceId(row.getUUID("service_id"));
            visionPrescription.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            visionPrescription.setMetaData(row.getMap("meta_data", String.class, String.class));
            visionPrescription.setEntryData(row.getString("entry_data"));
            visionPrescription.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(visionPrescription);
        }

        return results;
    }

    public void add(VisionPrescription visionPrescription) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("vision_prescription_id", visionPrescription.getVisionPrescriptionId())
                .addColumnUUID("patient_id", visionPrescription.getPatientId())
                .addColumnUUID("service_id", visionPrescription.getServiceId())
                .addColumnTimestamp("effective_datetime", visionPrescription.getEffectiveDateTime())
                .addColumnMap("meta_data", visionPrescription.getMetaData())
                .addColumnString("entry_data", visionPrescription.getEntryData())
                .addColumnTimestamp("last_updated", visionPrescription.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
