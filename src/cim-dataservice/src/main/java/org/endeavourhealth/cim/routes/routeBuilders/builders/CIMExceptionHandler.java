package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.ExceptionHandlerProcessor;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

public class CIMExceptionHandler extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_EXCEPTION_HANDLER))
                .routeId(CoreRouteName.CIM_EXCEPTION_HANDLER)
                .process(new ExceptionHandlerProcessor())
                .to(Route.direct(CoreRouteName.CIM_RESPONSE));
    }
}
