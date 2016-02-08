package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.processors.common.SecurityProcessor;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings("unused")
public class Security extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.SECURITY))
            .routeId(ComponentRouteName.SECURITY)
            .process(new SecurityProcessor());
    }
}
