package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.endeavourhealth.cim.processor.core.SecurityProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMSecurity extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_SECURITY))
                .routeId(CoreRouteName.CIM_SECURITY)
                .process(new SecurityProcessor())
                .to(Route.direct(CoreRouteName.CIM_SECURITY_RESULT));
    }
}
