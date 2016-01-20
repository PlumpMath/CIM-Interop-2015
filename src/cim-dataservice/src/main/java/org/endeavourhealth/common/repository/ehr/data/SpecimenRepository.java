package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Specimen;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpecimenRepository extends Repository {
	private static final String TableName = "specimen";

	public SpecimenRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Specimen> getSpecimensByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Specimen> results = new ArrayList<>();

        for (Row row : rows) {
            Specimen specimen = new Specimen();
            specimen.setSpecimenId(row.getUUID("specimen_id"));
            specimen.setPatientId(row.getUUID("patient_id"));
            specimen.setServiceId(row.getUUID("service_id"));
            specimen.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            specimen.setMetaData(row.getMap("meta_data", String.class, String.class));
            specimen.setEntryData(row.getString("entry_data"));
            specimen.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(specimen);
        }

        return results;
    }

    public void add(Specimen specimen) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("specimen_id", specimen.getSpecimenId())
                .addColumnUUID("patient_id", specimen.getPatientId())
                .addColumnUUID("service_id", specimen.getServiceId())
                .addColumnTimestamp("effective_datetime", specimen.getEffectiveDateTime())
                .addColumnMap("meta_data", specimen.getMetaData())
                .addColumnString("entry_data", specimen.getEntryData())
                .addColumnTimestamp("last_updated", specimen.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
