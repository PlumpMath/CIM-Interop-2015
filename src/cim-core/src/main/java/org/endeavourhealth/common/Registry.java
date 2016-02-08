package org.endeavourhealth.common;

import org.endeavourhealth.transform.EmisTransformer;
import org.endeavourhealth.transform.IRecordTransformer;
import org.endeavourhealth.core.repository.informationSharingProtocols.InformationSharingProtocol;

import java.util.*;

public class Registry implements IRegistry {

    ////////////////////////////////////////////////////////////
    // Items that need changing based on deployment
    ////////////////////////////////////////////////////////////

        private static final String EMIS_SOAP_URI = "http://localhost:9001/GPApiService/Soap";
        // private static final String EMIS_SOAP_URI = "http://endeavour-gp.cloudapp.net:9001/GPApiService/Soap";

        private static final String BASE_URI = "http://localhost:8080/api/0.1";
        // private static final String BASE_URI = "http://endeavour-cim.cloudapp.net/api/0.1";

        // ALSO CHANGE cim-apidoc/web/cim-api.json
        //
        // "host": "localhost:8080",
        // "host": "endeavour-cim.cloudapp.net",
        //
        // AND UPDATE "Build last updated" in the same file at
        //
        // "info": {
        //      "description"

    ////////////////////////////////////////////////////////////
    // End
    ////////////////////////////////////////////////////////////


    private static IRegistry _instance;

	public static IRegistry Instance() {

		if (_instance == null)
			_instance = new Registry();

		return _instance;
	}

	public static void setInstance(IRegistry registry) {

        _instance = registry;
	}

    public String getDataManagerTypeNameForService(String odsCode) throws ClassNotFoundException {

        return "org.endeavourhealth.cim.dataManager.emis.EmisDataManager";
    }

	public ArrayList<String> getAllDataManagerTypes() {

        ArrayList<String> dataManagerTypes = new ArrayList<>();
        dataManagerTypes.add("org.endeavourhealth.cim.dataManager.emis.EmisDataManager");

		// TODO : Move data manager list to db
        /*dataManagerTypes.add("EmisDataManager");
        dataManagerTypes.add("EmisDataManager"); */

        return dataManagerTypes;
    }

    public String getEmisSoapUri() {
        return EMIS_SOAP_URI;
    }

    public String getBaseUri(String odsCode) {
        return BASE_URI + "/" + odsCode;
    }

	@Override
	public IRecordTransformer getTransformerForContentType(String contentType) {
		return new EmisTransformer();
	}

	@Override
	public InformationSharingProtocol[] getDataDistributionProtocolsForApiKey(String apiKey) {
		return new InformationSharingProtocol[1];
	}
}
