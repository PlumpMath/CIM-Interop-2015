package org.endeavourhealth.cim.processor.observation;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.common.IDataAdapter;
import org.endeavourhealth.cim.common.ITransformer;
import org.endeavourhealth.cim.transform.TransformerFactory;

public class AddObservation implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        int serviceId = Integer.parseInt((String) exchange.getIn().getHeader("serviceId"));

        ITransformer transformer = TransformerFactory.getTransformerForService(serviceId);
        String request = transformer.fromFHIRObservation((String)exchange.getIn().getBody());

        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(serviceId);
        String response = dataAdapter.createObservation(request);

        exchange.getIn().setBody(transformer.toFHIRObservation(response));
    }
}
