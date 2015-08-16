package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.endeavourhealth.cim.processor.core.DataProtocolFilterProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMDataProtocolFilter extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL_FILTER))
            .routeId(CoreRouteName.CIM_DATA_PROTOCOL_FILTER)
            .process(new DataProtocolFilterProcessor())
            .to(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL_FILTER_RESULT));
    }
}
