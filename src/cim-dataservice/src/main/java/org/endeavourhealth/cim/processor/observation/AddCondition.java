package org.endeavourhealth.cim.processor.observation;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.TransformerBase;
import org.endeavourhealth.cim.transform.TransformerFactory;

public class AddCondition implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String serviceId =(String) exchange.getIn().getHeader("serviceId");

        TransformerBase transformer = TransformerFactory.getTransformerForService(serviceId);
        String request = transformer.fromFHIRCondition((String) exchange.getIn().getBody());

        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(serviceId);
        String response = dataAdapter.createCondition(request);

        exchange.getIn().setBody(transformer.toFHIRCondition(response));
    }
}
