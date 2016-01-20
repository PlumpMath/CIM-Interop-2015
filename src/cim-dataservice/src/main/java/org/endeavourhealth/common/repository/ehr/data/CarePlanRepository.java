package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.CarePlan;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarePlanRepository extends Repository {
	private static final String TableName = "care_plan";

	public CarePlanRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<CarePlan> getCarePlansByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<CarePlan> results = new ArrayList<>();

        for (Row row : rows) {
            CarePlan carePlan = new CarePlan();
            carePlan.setCarePlanId(row.getUUID("care_plan_id"));
            carePlan.setPatientId(row.getUUID("patient_id"));
            carePlan.setServiceId(row.getUUID("service_id"));
            carePlan.setEffectiveDateTime(row.getTimestamp("effective_datetime"));
            carePlan.setMetaData(row.getMap("meta_data", String.class, String.class));
            carePlan.setEntryData(row.getString("entry_data"));
            carePlan.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(carePlan);
        }

        return results;
    }

    public void add(CarePlan carePlan) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("care_plan_id", carePlan.getCarePlanId())
                .addColumnUUID("patient_id", carePlan.getPatientId())
                .addColumnUUID("service_id", carePlan.getServiceId())
                .addColumnTimestamp("effective_datetime", carePlan.getEffectiveDateTime())
                .addColumnMap("meta_data", carePlan.getMetaData())
                .addColumnString("entry_data", carePlan.getEntryData())
                .addColumnTimestamp("last_updated", carePlan.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
