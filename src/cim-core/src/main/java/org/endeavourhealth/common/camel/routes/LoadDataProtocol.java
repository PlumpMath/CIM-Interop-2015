package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.processor.LoadDataProtocols;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class LoadDataProtocol extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.LOAD_DATA_PROTOCOL))
            .routeId(ComponentRouteName.LOAD_DATA_PROTOCOL)
            .process(new LoadDataProtocols());
    }
}
