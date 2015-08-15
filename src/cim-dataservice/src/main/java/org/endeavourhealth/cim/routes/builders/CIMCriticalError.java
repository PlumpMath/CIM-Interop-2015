package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("unused")
public class CIMCriticalError extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_CRITICAL_ERROR))
            .routeId(CoreRouteName.CIM_CRITICAL_ERROR)
            .process(new CIMError(HttpStatus.SC_INTERNAL_SERVER_ERROR, simple("Critical error - please review server logs.")))
            .log(LoggingLevel.ERROR, "!!!CRITICAL ERROR!!!")
            .stop();
    }
}
