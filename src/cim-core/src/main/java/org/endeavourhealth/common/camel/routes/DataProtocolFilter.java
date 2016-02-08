package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.processor.DataProtocolFilterProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class DataProtocolFilter extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.DATA_PROTOCOL_FILTER))
            .routeId(ComponentRouteName.DATA_PROTOCOL_FILTER)
            .process(new DataProtocolFilterProcessor());
    }
}
