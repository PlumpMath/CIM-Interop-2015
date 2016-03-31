package org.endeavourhealth.repository;

import org.endeavourhealth.cim.dataManager.IRegistry;

public class TestRegistry implements IRegistry
{
    public String getDataManagerTypeNameForService(String odsCode) {
        return "org.endeavourhealth.cim.dataManager.EmisTestDataManager";
    }

	public ArrayList<String> getAllDataManagerTypes() {
        ArrayList<String> adapterTypes = new ArrayList<>();

        adapterTypes.add("org.endeavourhealth.cim.dataManager.TestEmisDataManager");
        adapterTypes.add("org.endeavourhealth.cim.dataManager.TestEmisDataManager");
        adapterTypes.add("org.endeavourhealth.cim.dataManager.TestEmisDataManager");

        return adapterTypes;
    }

    @Override
    public String getEmisSoapUri() {
        return null;
    }

    @Override
    public String getBaseUri(String odsCode) {
        return null;
    }

	@Override
	public IRecordTransformer getTransformerForContentType(String contentType) {
		return null;
	}

	@Override
	public InformationSharingProtocol[] getDataDistributionProtocolsForApiKey(String apiKey) {
		return new InformationSharingProtocol[0];
	}
}
