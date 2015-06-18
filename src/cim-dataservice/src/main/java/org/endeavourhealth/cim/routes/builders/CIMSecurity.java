package org.endeavourhealth.cim.routes.builders;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.ExceptionHandlerBaseRouteBuilder;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.SecurityProcessor;

public class CIMSecurity extends ExceptionHandlerBaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMSecurity")
            .routeId("CIMSecurity")
            .doTry()
                .process(new SecurityProcessor())
            .doCatch(Exception.class)
                .process(new CIMError(HttpStatus.SC_UNAUTHORIZED, simple("Invalid session")))
                .stop()
            .end()
            .to("direct:CIMSecurityResult");
    }
}
