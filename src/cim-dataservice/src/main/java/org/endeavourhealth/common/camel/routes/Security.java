package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.processor.SecurityProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class Security extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.SECURITY))
            .routeId(ComponentRouteName.SECURITY)
            .process(new SecurityProcessor());
    }
}
