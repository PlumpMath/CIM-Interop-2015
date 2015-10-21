package org.endeavourhealth.common.routes.core;

import org.endeavourhealth.common.processor.DataProtocolFilterProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings("unused")
public class DataProtocolFilter extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.DATA_PROTOCOL_FILTER))
            .routeId(CoreRouteName.DATA_PROTOCOL_FILTER)
            .process(new DataProtocolFilterProcessor());
    }
}
