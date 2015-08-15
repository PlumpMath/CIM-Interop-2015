package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.DataProtocolFilterProcessor;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("unused")
public class CIMDataProtocolFilter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL_FILTER))
            .routeId(CoreRouteName.CIM_DATA_PROTOCOL_FILTER)
            .process(new DataProtocolFilterProcessor())
            .to(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL_FILTER_RESULT));
    }
}
