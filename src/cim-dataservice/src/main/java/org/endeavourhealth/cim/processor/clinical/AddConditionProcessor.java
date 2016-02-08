package org.endeavourhealth.cim.processor.clinical;

import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;

public class AddConditionProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.DestinationOdsCode);
        String requestBody = ExchangeHelper.getInBodyString(exchange);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String responseBody = dataManager.addCondition(odsCode, requestBody);

        ExchangeHelper.setInBodyString(exchange, responseBody);
    }
}
