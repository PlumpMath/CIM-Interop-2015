package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.processor.ExceptionHandlerProcessor;
import org.endeavourhealth.common.core.ComponentRouteName;


@SuppressWarnings("unused")
public class ExceptionHandler extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.EXCEPTION_HANDLER))
                .routeId(ComponentRouteName.EXCEPTION_HANDLER)
                .process(new ExceptionHandlerProcessor())
                .to(direct(ComponentRouteName.RESPONSE));
    }
}
