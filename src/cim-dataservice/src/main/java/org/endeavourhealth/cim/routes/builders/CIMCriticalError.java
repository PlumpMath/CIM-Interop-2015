package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.processor.core.CIMError;

@SuppressWarnings("unused")
public class CIMCriticalError extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMCriticalError")
            .process(new CIMError(HttpStatus.SC_INTERNAL_SERVER_ERROR, simple("Critical error - please review server logs.")))
            .log(LoggingLevel.ERROR, "!!!CRITICAL ERROR!!!")
            .stop();
    }
}
