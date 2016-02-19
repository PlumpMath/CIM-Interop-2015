package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.helpers.PropertyKey;
import org.endeavourhealth.cim.camel.processors.common.ResponseProcessor;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Response extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {
		// Perform response channel functionality when applicable
        from(direct(ComponentRouteName.RESPONSE))
            .routeId(ComponentRouteName.RESPONSE)
            .process(new ResponseProcessor())
            .setProperty(PropertyKey.TapLocation, constant("Outbound"))
            .wireTap(BaseRouteBuilder.direct(ComponentRouteName.AUDIT))
            .end()
			.recipientList(simple("${header.response-url}"));
    }
}
