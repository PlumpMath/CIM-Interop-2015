package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Substance;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubstanceRepository extends Repository {
	private static final String TableName = "substance";

	public SubstanceRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Substance> getSubstancesBySubstanceId(UUID substanceId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("substance_id", substanceId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Substance> results = new ArrayList<>();

        for (Row row : rows) {
            Substance substance = new Substance();
            substance.setSubstanceId(row.getUUID("substance_id"));
            substance.setMetaData(row.getMap("meta_data", String.class, String.class));
            substance.setEntryData(row.getString("entry_data"));
            substance.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(substance);
        }

        return results;
    }

    public void add(Substance substance) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("substance_id", substance.getSubstanceId())
                .addColumnMap("meta_data", substance.getMetaData())
                .addColumnString("entry_data", substance.getEntryData())
                .addColumnTimestamp("last_updated", substance.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
