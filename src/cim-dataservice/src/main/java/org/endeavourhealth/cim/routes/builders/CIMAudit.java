package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.AuditProcessor;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CIMAudit extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_AUDIT))
            .routeId(CoreRouteName.CIM_AUDIT)
            .process(new AuditProcessor());
    }
}
