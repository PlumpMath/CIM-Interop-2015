package org.endeavourhealth.cim.camel.routes.common;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings({"WeakerAccess", "unused"})
public class InvalidMessage extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.INVALID_MESSAGE))
            .routeId(ComponentRouteName.INVALID_MESSAGE)
            .log(LoggingLevel.ERROR, "InvalidMessage")
            .to(direct(ComponentRouteName.RESPONSE))
            .stop();
    }
}
