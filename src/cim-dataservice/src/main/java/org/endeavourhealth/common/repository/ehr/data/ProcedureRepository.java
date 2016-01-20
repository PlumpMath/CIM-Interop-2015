package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Procedure;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcedureRepository extends Repository {
	private static final String TableName = "procedure";

	public ProcedureRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Procedure> getProceduresByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Procedure> results = new ArrayList<>();

        for (Row row : rows) {
            Procedure procedure = new Procedure();
            procedure.setProcedureId(row.getUUID("procedure_id"));
            procedure.setPatientId(row.getUUID("patient_id"));
            procedure.setServiceId(row.getUUID("service_id"));
            procedure.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            procedure.setMetaData(row.getMap("meta_data", String.class, String.class));
            procedure.setEntryData(row.getString("entry_data"));
            procedure.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(procedure);
        }

        return results;
    }

    public void add(Procedure procedure) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("procedure_id", procedure.getProcedureId())
                .addColumnUUID("patient_id", procedure.getPatientId())
                .addColumnUUID("service_id", procedure.getServiceId())
                .addColumnTimestamp("effective_datetime", procedure.getEffectiveDateTime())
                .addColumnMap("meta_data", procedure.getMetaData())
                .addColumnString("entry_data", procedure.getEntryData())
                .addColumnTimestamp("last_updated", procedure.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
