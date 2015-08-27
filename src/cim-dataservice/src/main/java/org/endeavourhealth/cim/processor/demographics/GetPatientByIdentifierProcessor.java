package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class GetPatientByIdentifierProcessor implements org.apache.camel.Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
        String identifier = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Identifier);

        String nhsNumber = identifier.substring(identifier.indexOf('|')+1);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String requestBody = dataManager.getPatientDemographicsByNhsNumber(odsCode, nhsNumber);

        ExchangeHelper.setInBodyString(exchange, requestBody);
    }
}
