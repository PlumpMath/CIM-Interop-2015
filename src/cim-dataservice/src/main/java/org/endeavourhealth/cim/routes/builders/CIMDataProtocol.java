package org.endeavourhealth.cim.routes.builders;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.ExceptionHandlerBaseRouteBuilder;
import org.endeavourhealth.cim.exceptions.LegitimateRelationshipException;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.DataProtocolProcessor;

@SuppressWarnings("unused")
public class CIMDataProtocol extends ExceptionHandlerBaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMDataProtocol")
            .routeId("CIMDataProtocol")
            .doTry()
                .process(new DataProtocolProcessor())
                .to("direct:CIMDataProtocolResult")
            .doCatch(LegitimateRelationshipException.class)
                .process(new CIMError(HttpStatus.SC_FORBIDDEN, simple("${exception.message}")))
            .end();
    }
}
