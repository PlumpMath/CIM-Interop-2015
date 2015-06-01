package org.endeavourhealth.cim.routes.builders;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.CIMRouteBuilder;
import org.endeavourhealth.cim.processor.core.CIMError;

public class CIMPayloadValidation extends CIMRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMPayloadValidation")
            .routeId("CIMPayloadValidation")
            .doTry()
//                .to("validator:payloadSchema.xsd")
                .to("direct:CIMPayloadValidationResult")
            .doCatch(org.apache.camel.ValidationException.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("Invalid payload")))
                .stop()
            .end();

    }
}
