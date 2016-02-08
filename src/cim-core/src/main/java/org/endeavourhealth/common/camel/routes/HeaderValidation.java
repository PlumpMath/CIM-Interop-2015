package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.processor.HeaderValidationProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class HeaderValidation extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.HEADER_VALIDATION))
            .routeId(ComponentRouteName.HEADER_VALIDATION)
            .process(new HeaderValidationProcessor());
    }
}
