package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.SecurityProcessor;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("unused")
public class CIMSecurity extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_SECURITY))
            .routeId(CoreRouteName.CIM_SECURITY)
            .doTry()
                .process(new SecurityProcessor())
            .doCatch(Exception.class)
                .process(new CIMError(HttpStatus.SC_UNAUTHORIZED, simple("${exception.message}")))
				.to(Route.direct(CoreRouteName.CIM_INVALID_MESSAGE))
            .end()
			.to(Route.direct(CoreRouteName.CIM_SECURITY_RESULT));
    }
}
