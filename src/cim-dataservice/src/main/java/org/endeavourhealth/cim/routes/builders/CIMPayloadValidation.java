package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("unused")
public class CIMPayloadValidation extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_PAYLOAD_VALIDATION))
            .routeId(CoreRouteName.CIM_PAYLOAD_VALIDATION)
            .doTry()
                .to("mock:PayloadValidation")
            .doCatch(org.apache.camel.ValidationException.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("Invalid payload")))
                .to(Route.direct(CoreRouteName.CIM_INVALID_MESSAGE))
            .end()
            .to(Route.direct(CoreRouteName.CIM_PAYLOAD_VALIDATION_RESULT));
    }
}
