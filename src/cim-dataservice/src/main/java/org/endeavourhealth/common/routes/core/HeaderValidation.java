package org.endeavourhealth.common.routes.core;

import org.endeavourhealth.common.processor.HeaderValidationProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings("unused")
public class HeaderValidation extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.HEADER_VALIDATION))
            .routeId(CoreRouteName.HEADER_VALIDATION)
            .process(new HeaderValidationProcessor());
    }
}
