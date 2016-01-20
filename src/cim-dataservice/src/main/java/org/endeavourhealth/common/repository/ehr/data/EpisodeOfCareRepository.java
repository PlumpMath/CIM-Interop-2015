package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.EpisodeOfCare;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EpisodeOfCareRepository extends Repository {
	private static final String TableName = "episode_of_care";

	public EpisodeOfCareRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<EpisodeOfCare> getEpisodeOfCaresByPatientId(UUID patientId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("patient_id", patientId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<EpisodeOfCare> results = new ArrayList<>();

        for (Row row : rows) {
            EpisodeOfCare episodeOfCare = new EpisodeOfCare();
            episodeOfCare.setEpisodeOfCareId(row.getUUID("episode_of_care_id"));
            episodeOfCare.setPatientId(row.getUUID("patient_id"));
            episodeOfCare.setServiceId(row.getUUID("service_id"));
            episodeOfCare.setMetaData(row.getMap("meta_data", String.class, String.class));
            episodeOfCare.setEntryData(row.getString("entry_data"));
            episodeOfCare.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(episodeOfCare);
        }

        return results;
    }

    public void add(EpisodeOfCare episodeOfCare) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("episode_of_care_id", episodeOfCare.getEpisodeOfCareId())
                .addColumnUUID("patient_id", episodeOfCare.getPatientId())
                .addColumnUUID("service_id", episodeOfCare.getServiceId())
                .addColumnMap("meta_data", episodeOfCare.getMetaData())
                .addColumnString("entry_data", episodeOfCare.getEntryData())
                .addColumnTimestamp("last_updated", episodeOfCare.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
