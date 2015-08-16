package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMPayloadValidation extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_PAYLOAD_VALIDATION))
            .routeId(CoreRouteName.CIM_PAYLOAD_VALIDATION)
            .to("mock:PayloadValidation")
            .to(Route.direct(CoreRouteName.CIM_PAYLOAD_VALIDATION_RESULT));
    }
}
