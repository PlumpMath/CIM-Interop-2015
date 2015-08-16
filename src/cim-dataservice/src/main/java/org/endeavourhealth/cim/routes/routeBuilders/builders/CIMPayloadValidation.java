package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMPayloadValidation extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_PAYLOAD_VALIDATION))
            .routeId(CoreRouteName.CIM_PAYLOAD_VALIDATION)
            .to("mock:PayloadValidation")
            .to(Route.direct(CoreRouteName.CIM_PAYLOAD_VALIDATION_RESULT));
    }
}
