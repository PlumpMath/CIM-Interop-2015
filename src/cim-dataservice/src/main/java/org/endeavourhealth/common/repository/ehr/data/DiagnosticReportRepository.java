package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.DiagnosticReport;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DiagnosticReportRepository extends Repository {
	private static final String TableName = "diagnostic_report";

	public DiagnosticReportRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<DiagnosticReport> getDiagnosticReportsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<DiagnosticReport> results = new ArrayList<>();

        for (Row row : rows) {
            DiagnosticReport diagnosticReport = new DiagnosticReport();
            diagnosticReport.setDiagnosticReportId(row.getUUID("diagnostic_report_id"));
            diagnosticReport.setPatientId(row.getUUID("patient_id"));
            diagnosticReport.setServiceId(row.getUUID("service_id"));
            diagnosticReport.setDateEffective(row.getTimestamp("date_effective"));
            diagnosticReport.setMetaData(row.getMap("meta_data", String.class, String.class));
            diagnosticReport.setEntryData(row.getString("entry_data"));
            diagnosticReport.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(diagnosticReport);
        }

        return results;
    }

    public void add(DiagnosticReport diagnosticReport) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("diagnostic_report_id", diagnosticReport.getDiagnosticReportId())
                .addColumnUUID("patient_id", diagnosticReport.getPatientId())
                .addColumnUUID("service_id", diagnosticReport.getServiceId())
                .addColumnTimestamp("date_effective", diagnosticReport.getDateEffective())
                .addColumnMap("meta_data", diagnosticReport.getMetaData())
                .addColumnString("entry_data", diagnosticReport.getEntryData())
                .addColumnTimestamp("last_updated", diagnosticReport.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
