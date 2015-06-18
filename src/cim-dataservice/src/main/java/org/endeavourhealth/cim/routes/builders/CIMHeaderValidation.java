package org.endeavourhealth.cim.routes.builders;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.ExceptionHandlerBaseRouteBuilder;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.HeaderValidationProcessor;

public class CIMHeaderValidation extends ExceptionHandlerBaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMHeaderValidation")
            .routeId("CIMHeaderValidation")
            .doTry()
                .process(new HeaderValidationProcessor())
            .doCatch(Exception.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("Invalid header : ${exception.message}")))
                .stop()
            .end()
            .to("direct:CIMHeaderValidationResult");
    }
}
