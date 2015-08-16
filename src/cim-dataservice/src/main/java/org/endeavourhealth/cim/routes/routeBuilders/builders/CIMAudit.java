package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.endeavourhealth.cim.processor.core.AuditProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CIMAudit extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_AUDIT))
            .routeId(CoreRouteName.CIM_AUDIT)
            .process(new AuditProcessor());
    }
}
