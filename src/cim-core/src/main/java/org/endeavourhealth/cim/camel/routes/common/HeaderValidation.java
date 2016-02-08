package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.processors.common.HeaderValidationProcessor;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings("unused")
public class HeaderValidation extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.HEADER_VALIDATION))
            .routeId(ComponentRouteName.HEADER_VALIDATION)
            .process(new HeaderValidationProcessor());
    }
}
