package org.endeavourhealth.core.repository.informationSharingProtocols;

import com.datastax.driver.core.Row;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.data.Repository;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.core.repository.common.data.StringKeyRepositoryHelper;

import java.util.UUID;

public class InformationSharingProtocolRepository extends  Repository {
	private static final String TableName = "information_sharing_protocol";
	private static InformationSharingProtocolRepository _instance;

	public static InformationSharingProtocolRepository getInstance() {
		if (_instance == null)
			_instance = new InformationSharingProtocolRepository();

		return _instance;
	}

	public static void setInstance(InformationSharingProtocolRepository instance) {
		_instance = instance;
	}

	public InformationSharingProtocolRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public InformationSharingProtocol getByid(UUID id) throws RepositoryException {
		if (id == null)
			return null;

		Row row = StringKeyRepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), TableName, "channelName", id.toString());

		if (row == null)
			return null;

		return populateInformationSharingProtocol(row);
	}

	private InformationSharingProtocol populateInformationSharingProtocol(Row row) {
		InformationSharingProtocol result = new InformationSharingProtocol();
		result.setId(row.getUUID("id"));
		result.setData(row.getString("data"));
		result.setDataSchemaVersion(row.getString("schema_version"));
		return result;
	}
}
