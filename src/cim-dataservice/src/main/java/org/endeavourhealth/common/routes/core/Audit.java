package org.endeavourhealth.common.routes.core;

import org.endeavourhealth.common.processor.AuditProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Audit extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.AUDIT))
            .routeId(CoreRouteName.AUDIT)
            .process(new AuditProcessor());
    }
}
