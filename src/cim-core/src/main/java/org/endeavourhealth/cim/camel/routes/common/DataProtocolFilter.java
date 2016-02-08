package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.processors.common.DataProtocolFilterProcessor;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings("unused")
public class DataProtocolFilter extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.DATA_PROTOCOL_FILTER))
            .routeId(ComponentRouteName.DATA_PROTOCOL_FILTER)
            .process(new DataProtocolFilterProcessor());
    }
}
