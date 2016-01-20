package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.AppointmentResponse;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentResponseRepository extends Repository {
	private static final String TableName = "appointment_response";

	public AppointmentResponseRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<AppointmentResponse> getAppointmentResponsesByAppointmentId(UUID appointmentId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("appointment_id", appointmentId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<AppointmentResponse> results = new ArrayList<>();

        for (Row row : rows) {
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            appointmentResponse.setAppointmentResponseId(row.getUUID("appointment_response_id"));
            appointmentResponse.setAppointmentId(row.getUUID("appointment_id"));
            appointmentResponse.setMetaData(row.getMap("meta_data", String.class, String.class));
            appointmentResponse.setEntryData(row.getString("entry_data"));
            appointmentResponse.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(appointmentResponse);
        }

        return results;
    }

    public void add(AppointmentResponse appointmentResponse) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("appointment_response_id", appointmentResponse.getAppointmentResponseId())
                .addColumnUUID("appointment_id", appointmentResponse.getAppointmentId())
                .addColumnMap("meta_data", appointmentResponse.getMetaData())
                .addColumnString("entry_data", appointmentResponse.getEntryData())
                .addColumnTimestamp("last_updated", appointmentResponse.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
