package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Appointment;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentRepository extends Repository {
	private static final String TableName = "appointment";

	public AppointmentRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Appointment> getAppointmentsByAppointmentId(UUID appointmentId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("appointment_id", appointmentId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Appointment> results = new ArrayList<>();

        for (Row row : rows) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(row.getUUID("appointment_id"));
            appointment.setMetaData(row.getMap("meta_data", String.class, String.class));
            appointment.setEntryData(row.getString("entry_data"));
            appointment.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(appointment);
        }

        return results;
    }

    public void add(Appointment appointment) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("appointment_id", appointment.getAppointmentId())
                .addColumnMap("meta_data", appointment.getMetaData())
                .addColumnString("entry_data", appointment.getEntryData())
                .addColumnTimestamp("last_updated", appointment.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
