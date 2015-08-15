package org.endeavourhealth.cim.processor.clinical;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.processor.CIMProcessor;

public class AddConditionProcessor extends CIMProcessor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = getInHeaderString(exchange, HeaderKey.OdsCode);
        String fhirRequest = getInBodyString(exchange);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String response = dataManager.createCondition(odsCode, fhirRequest);

        setInBodyString(exchange, response);
    }
}
