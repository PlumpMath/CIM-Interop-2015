package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings("unused")
public class PayloadValidation extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.PAYLOAD_VALIDATION))
            .routeId(ComponentRouteName.PAYLOAD_VALIDATION)
            .to("mock:PayloadValidation");
    }
}
