package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.processor.AuditProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Audit extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.AUDIT))
            .routeId(ComponentRouteName.AUDIT)
            .process(new AuditProcessor());
    }
}
