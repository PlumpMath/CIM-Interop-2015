package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Immunization;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImmunizationRepository extends Repository {
	private static final String TableName = "immunization";

	public ImmunizationRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Immunization> getImmunizationsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Immunization> results = new ArrayList<>();

        for (Row row : rows) {
            Immunization immunization = new Immunization();
            immunization.setImmunizationId(row.getUUID("immunization_id"));
            immunization.setPatientId(row.getUUID("patient_id"));
            immunization.setServiceId(row.getUUID("service_id"));
            immunization.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            immunization.setMetaData(row.getMap("meta_data", String.class, String.class));
            immunization.setEntryData(row.getString("entry_data"));
            immunization.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(immunization);
        }

        return results;
    }

    public void add(Immunization immunization) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("immunization_id", immunization.getImmunizationId())
                .addColumnUUID("patient_id", immunization.getPatientId())
                .addColumnUUID("service_id", immunization.getServiceId())
                .addColumnTimestamp("effective_datetime", immunization.getEffectiveDateTime())
                .addColumnMap("meta_data", immunization.getMetaData())
                .addColumnString("entry_data", immunization.getEntryData())
                .addColumnTimestamp("last_updated", immunization.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
