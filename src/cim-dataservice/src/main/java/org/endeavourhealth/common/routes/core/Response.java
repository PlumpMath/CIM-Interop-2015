package org.endeavourhealth.common.routes.core;

import org.endeavourhealth.common.processor.ResponseProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Response extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.RESPONSE))
            .routeId(CoreRouteName.RESPONSE)
            .process(new ResponseProcessor());
    }
}
