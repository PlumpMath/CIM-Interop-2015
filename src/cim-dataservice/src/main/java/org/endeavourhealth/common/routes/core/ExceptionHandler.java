package org.endeavourhealth.common.routes.core;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.common.processor.ExceptionHandlerProcessor;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings("unused")
public class ExceptionHandler extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.EXCEPTION_HANDLER))
                .routeId(CoreRouteName.EXCEPTION_HANDLER)
                .process(new ExceptionHandlerProcessor())
                .to(Route.direct(CoreRouteName.RESPONSE));
    }
}
