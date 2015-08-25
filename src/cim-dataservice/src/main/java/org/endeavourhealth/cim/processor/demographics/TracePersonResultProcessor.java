package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.hl7.fhir.instance.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class TracePersonResultProcessor implements org.apache.camel.Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        List<String> bundles = (ArrayList<String>)ExchangeHelper.getInBodyObject(exchange);

        if (bundles.size() == 1) {
            ExchangeHelper.setInBodyString(exchange, bundles.get(0));
        }
        else {
            // combine bundles
        }
    }
}
