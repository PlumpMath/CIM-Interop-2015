package org.endeavourhealth.common.routes.core;

import org.endeavourhealth.common.processor.SecurityProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings("unused")
public class Security extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.SECURITY))
            .routeId(CoreRouteName.SECURITY)
            .process(new SecurityProcessor());
    }
}
