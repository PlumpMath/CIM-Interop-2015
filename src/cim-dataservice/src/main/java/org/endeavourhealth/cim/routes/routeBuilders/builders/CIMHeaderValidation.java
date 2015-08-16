package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.HeaderValidationProcessor;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMHeaderValidation extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_HEADER_VALIDATION))
            .routeId(CoreRouteName.CIM_HEADER_VALIDATION)
            .process(new HeaderValidationProcessor())
            .to(Route.direct(CoreRouteName.CIM_HEADER_VALIDATION_RESULT));
    }
}
