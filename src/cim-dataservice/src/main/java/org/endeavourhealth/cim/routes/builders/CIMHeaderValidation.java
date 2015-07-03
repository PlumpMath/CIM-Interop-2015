package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.HeaderValidationProcessor;

@SuppressWarnings("unused")
public class CIMHeaderValidation extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMHeaderValidation")
            .routeId("CIMHeaderValidation")
            .doTry()
                .process(new HeaderValidationProcessor())
            .doCatch(Exception.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("${exception.message}")))
                .to("direct:CIMInvalidMessage")
            .end()
            .to("direct:CIMHeaderValidationResult");
    }
}
