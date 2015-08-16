package org.endeavourhealth.cim.routes.common;

import org.apache.camel.builder.RouteBuilder;

public abstract class CIMRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .to(Route.direct(CoreRouteName.CIM_EXCEPTION_HANDLER))
                .handled(true);

        configureRoute();
    }

    public abstract void configureRoute() throws Exception;
}
