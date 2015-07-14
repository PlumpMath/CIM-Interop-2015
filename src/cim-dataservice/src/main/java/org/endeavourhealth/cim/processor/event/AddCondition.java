package org.endeavourhealth.cim.processor.event;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.io.InputStream;

public class AddCondition implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String odsCode =(String) exchange.getIn().getHeader(HeaderKey.OdsCode);
        String fhirRequest = IOUtils.toString((InputStream) exchange.getIn().getBody());

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);

        String response = dataManager.createCondition(odsCode, fhirRequest);

        exchange.getIn().setBody(response);
    }
}
