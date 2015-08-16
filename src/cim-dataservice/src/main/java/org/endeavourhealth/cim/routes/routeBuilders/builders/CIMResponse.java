package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.ResponseProcessor;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CIMResponse extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_RESPONSE))
            .routeId(CoreRouteName.CIM_RESPONSE)
            .process(new ResponseProcessor());
    }
}
