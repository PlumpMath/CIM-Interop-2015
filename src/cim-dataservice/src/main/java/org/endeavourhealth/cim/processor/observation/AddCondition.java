package org.endeavourhealth.cim.processor.observation;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.TransformerBase;
import org.endeavourhealth.cim.transform.TransformerFactory;

import java.io.InputStream;

public class AddCondition implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String odsCode =(String) exchange.getIn().getHeader("odsCode");

        TransformerBase transformer = TransformerFactory.getTransformerForService(odsCode);
        String messageBody = IOUtils.toString((InputStream) exchange.getIn().getBody());

        String request = transformer.fromFHIRCondition(messageBody);

        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
        String response = dataAdapter.createCondition(request);

        exchange.getIn().setBody(transformer.toFHIRCondition(response));
    }
}
