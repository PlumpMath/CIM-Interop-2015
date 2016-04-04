package org.endeavourhealth.cim.camel.processors.demographics;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class GetPatientByIdentifierProcessor implements org.apache.camel.Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode);
        String identifier = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Identifier);

        String nhsNumber = identifier.substring(identifier.indexOf('|')+1);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String requestBody = dataManager.getPatientDemographicsByNhsNumber(odsCode, nhsNumber);

        ExchangeHelper.setInBodyString(exchange, requestBody);
    }
}
