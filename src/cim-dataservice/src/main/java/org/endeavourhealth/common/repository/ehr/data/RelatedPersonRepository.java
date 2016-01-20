package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.RelatedPerson;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RelatedPersonRepository extends Repository {
	private static final String TableName = "related_person";

	public RelatedPersonRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<RelatedPerson> getRelatedPersonsByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<RelatedPerson> results = new ArrayList<>();

        for (Row row : rows) {
            RelatedPerson relatedPerson = new RelatedPerson();
            relatedPerson.setRelatedPersonId(row.getUUID("related_person_id"));
            relatedPerson.setPatientId(row.getUUID("patient_id"));
            relatedPerson.setMetaData(row.getMap("meta_data", String.class, String.class));
            relatedPerson.setEntryData(row.getString("entry_data"));
            relatedPerson.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(relatedPerson);
        }

        return results;
    }

    public void add(RelatedPerson relatedPerson) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("related_person_id", relatedPerson.getRelatedPersonId())
                .addColumnUUID("patient_id", relatedPerson.getPatientId())
                .addColumnMap("meta_data", relatedPerson.getMetaData())
                .addColumnString("entry_data", relatedPerson.getEntryData())
                .addColumnTimestamp("last_updated", relatedPerson.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
