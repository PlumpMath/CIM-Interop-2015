package org.endeavourhealth.cim.routes.builders;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.ExceptionHandlerBaseRouteBuilder;
import org.endeavourhealth.cim.processor.core.CIMError;

public class CIMPayloadValidation extends ExceptionHandlerBaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMPayloadValidation")
            .routeId("CIMPayloadValidation")
            .doTry()
                .to("mock:PayloadValidation")
            .doCatch(org.apache.camel.ValidationException.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("Invalid payload")))
                .stop()
            .end()
            .to("direct:CIMPayloadValidationResult");

    }
}
