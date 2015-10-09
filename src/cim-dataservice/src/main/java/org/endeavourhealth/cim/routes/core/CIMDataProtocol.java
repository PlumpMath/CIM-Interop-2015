package org.endeavourhealth.cim.routes.core;

import org.endeavourhealth.cim.processor.core.DataProtocolProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMDataProtocol extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL))
            .routeId(CoreRouteName.CIM_DATA_PROTOCOL)
            .process(new DataProtocolProcessor());
    }
}
