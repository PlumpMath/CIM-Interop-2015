package org.endeavourhealth.cim.routes.core;

import org.endeavourhealth.cim.processor.core.HeaderValidationProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMHeaderValidation extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_HEADER_VALIDATION))
            .routeId(CoreRouteName.CIM_HEADER_VALIDATION)
            .process(new HeaderValidationProcessor());
    }
}
