package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.processors.common.ExceptionHandlerProcessor;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;


@SuppressWarnings("unused")
public class ExceptionHandler extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.EXCEPTION_HANDLER))
                .routeId(ComponentRouteName.EXCEPTION_HANDLER)
                .process(new ExceptionHandlerProcessor())
                .to(direct(ComponentRouteName.RESPONSE));
    }
}
