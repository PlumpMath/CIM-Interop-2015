package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.CIMRouteBuilder;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.SecurityProcessor;

public class CIMSecurity extends CIMRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMSecurity")
            .routeId("CIMSecurity")
            .doTry()
                .process(new SecurityProcessor())
                .to("direct:CIMSecurityResult")
            .doCatch(Exception.class)
                .process(new CIMError(HttpStatus.SC_UNAUTHORIZED, simple("Invalid session")))
                .stop()
            .end();
    }
}
