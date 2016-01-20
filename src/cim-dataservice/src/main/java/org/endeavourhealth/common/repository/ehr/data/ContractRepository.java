package org.endeavourhealth.common.repository.ehr.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.endeavourhealth.common.repository.ehr.model.Contract;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContractRepository extends Repository {
	private static final String TableName = "contract";

	public ContractRepository() {
		super(DataConfiguration.DATASERVICE_EHR_KEYSPACE);
	}

    public List<Contract> getContractsByContractId(UUID contractId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(TableName)
                .where(QueryBuilder.eq("contract_id", contractId)));

        BoundStatement boundStatement = new BoundStatement(preparedStatement);

        List<Row> rows =  getSession().execute(boundStatement)
                .all();

        List<Contract> results = new ArrayList<>();

        for (Row row : rows) {
            Contract contract = new Contract();
            contract.setContractId(row.getUUID("contract_id"));
            contract.setMetaData(row.getMap("meta_data", String.class, String.class));
            contract.setEntryData(row.getString("entry_data"));
            contract.setLastUpdated(row.getTimestamp("last_updated"));

            results.add(contract);
        }

        return results;
    }

    public void add(Contract contract) throws RepositoryException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
                .addColumnUUID("contract_id", contract.getContractId())
                .addColumnMap("meta_data", contract.getMetaData())
                .addColumnString("entry_data", contract.getEntryData())
                .addColumnTimestamp("last_updated", contract.getLastUpdated())
                .build();

        getSession().execute(boundStatement);
    }



}
