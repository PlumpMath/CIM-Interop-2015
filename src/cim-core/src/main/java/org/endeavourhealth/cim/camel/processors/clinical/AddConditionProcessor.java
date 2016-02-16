package org.endeavourhealth.cim.camel.processors.clinical;

import org.apache.http.HttpStatus;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;

public class AddConditionProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode);
        String requestBody = ExchangeHelper.getInBodyString(exchange);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String responseBody = dataManager.addCondition(odsCode, requestBody);

        ExchangeHelper.setInBodyString(exchange, responseBody);
        ExchangeHelper.setInHeader(exchange, Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_CREATED);
    }
}
