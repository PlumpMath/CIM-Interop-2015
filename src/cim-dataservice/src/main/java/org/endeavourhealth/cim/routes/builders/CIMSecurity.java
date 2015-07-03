package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.SecurityProcessor;

@SuppressWarnings("unused")
public class CIMSecurity extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMSecurity")
            .routeId("CIMSecurity")
            .doTry()
                .process(new SecurityProcessor())
            .doCatch(Exception.class)
                .process(new CIMError(HttpStatus.SC_UNAUTHORIZED, simple("${exception.message}")))
				.to("direct:CIMInvalidMessage")
            .end()
			.to("direct:CIMSecurityResult");
    }
}
