package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class PayloadValidation extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.PAYLOAD_VALIDATION))
            .routeId(ComponentRouteName.PAYLOAD_VALIDATION)
            .to("mock:PayloadValidation");
    }
}
