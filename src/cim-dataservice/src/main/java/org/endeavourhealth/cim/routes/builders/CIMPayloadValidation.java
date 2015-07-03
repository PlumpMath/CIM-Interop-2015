package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.processor.core.CIMError;

@SuppressWarnings("unused")
public class CIMPayloadValidation extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMPayloadValidation")
            .routeId("CIMPayloadValidation")
            .doTry()
                .to("mock:PayloadValidation")
            .doCatch(org.apache.camel.ValidationException.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("Invalid payload")))
                .to("direct:CIMInvalidMessage")
            .end()
            .to("direct:CIMPayloadValidationResult");
    }
}
