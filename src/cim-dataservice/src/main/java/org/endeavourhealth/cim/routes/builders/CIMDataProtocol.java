package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.exceptions.LegitimateRelationshipException;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.DataProtocolProcessor;

@SuppressWarnings("unused")
public class CIMDataProtocol extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMDataProtocol")
            .routeId("CIMDataProtocol")
            .doTry()
                .process(new DataProtocolProcessor())
            .doCatch(LegitimateRelationshipException.class)
                .process(new CIMError(HttpStatus.SC_FORBIDDEN, simple("${exception.message}")))
				.to("direct:CIMInvalidMessage")
            .end()
			.to("direct:CIMDataProtocolResult");
    }
}
