package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.HeaderValidationProcessor;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("unused")
public class CIMHeaderValidation extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_HEADER_VALIDATION))
            .routeId(CoreRouteName.CIM_HEADER_VALIDATION)
            .doTry()
                .process(new HeaderValidationProcessor())
            .doCatch(Exception.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("${exception.message}")))
                .to(Route.direct(CoreRouteName.CIM_INVALID_MESSAGE))
            .end()
            .to(Route.direct(CoreRouteName.CIM_HEADER_VALIDATION_RESULT));
    }
}
