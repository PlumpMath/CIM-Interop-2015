package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.processor.DataProtocolProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class DataProtocol extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.DATA_PROTOCOL))
            .routeId(ComponentRouteName.DATA_PROTOCOL)
            .process(new DataProtocolProcessor());
    }
}
