package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.processors.common.LoadDataProtocols;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings("unused")
public class LoadDataProtocol extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.LOAD_DATA_PROTOCOL))
            .routeId(ComponentRouteName.LOAD_DATA_PROTOCOL)
            .process(new LoadDataProtocols());
    }
}
