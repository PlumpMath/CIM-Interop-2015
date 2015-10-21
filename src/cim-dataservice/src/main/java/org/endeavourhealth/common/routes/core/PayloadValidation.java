package org.endeavourhealth.common.routes.core;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings("unused")
public class PayloadValidation extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.PAYLOAD_VALIDATION))
            .routeId(CoreRouteName.PAYLOAD_VALIDATION)
            .to("mock:PayloadValidation");
    }
}
