package org.endeavourhealth.cim.processor.observation;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Condition;

import java.io.InputStream;

public class AddCondition implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String odsCode =(String) exchange.getIn().getHeader("odsCode");

        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
        Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
        String messageBody = IOUtils.toString((InputStream) exchange.getIn().getBody());

        Condition condition = (Condition)new JsonParser().parse(messageBody);

        String request = transformer.fromFHIRCondition(condition);

        String response = dataAdapter.createCondition(request);

        //exchange.getIn().setBody(transformer.toFHIRCondition(response));
    }
}
