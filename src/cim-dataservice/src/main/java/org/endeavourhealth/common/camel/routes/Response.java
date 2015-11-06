package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.processor.ResponseProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Response extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
		// Perform response channel functionality when applicable
        from(direct(ComponentRouteName.RESPONSE))
            .routeId(ComponentRouteName.RESPONSE)
            .process(new ResponseProcessor())
			.recipientList(simple("${header.response_uri}"));
    }
}
