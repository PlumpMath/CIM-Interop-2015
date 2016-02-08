package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.processors.common.AuditProcessor;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Audit extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.AUDIT))
            .routeId(ComponentRouteName.AUDIT)
            .process(new AuditProcessor());
    }
}
