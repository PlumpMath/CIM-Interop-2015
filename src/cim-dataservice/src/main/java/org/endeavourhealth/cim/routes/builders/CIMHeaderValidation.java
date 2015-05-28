package org.endeavourhealth.cim.routes.builders;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.CIMRouteBuilder;
import org.endeavourhealth.cim.processor.core.CIMError;

public class CIMHeaderValidation extends CIMRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMHeaderValidation")
            .routeId("CIMHeaderValidation")
            .doTry()
//                .to("validator:headerSchema.xsd")
                .to("direct:CIMHeaderValidationResult")
            .doCatch(org.apache.camel.ValidationException.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, "Invalid header"))
                .stop()
            .end();
    }
}
