package org.endeavourhealth.cim.processor.clinical;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class AddConditionProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
        String requestBody = ExchangeHelper.getInBodyString(exchange);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String responseBody = dataManager.createCondition(odsCode, requestBody);

        ExchangeHelper.setInBodyString(exchange, responseBody);
    }
}
