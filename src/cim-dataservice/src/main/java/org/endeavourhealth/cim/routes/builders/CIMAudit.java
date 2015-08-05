package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.AuditProcessor;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CIMAudit extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMAudit")
            .routeId("CIMAudit")
            .process(new AuditProcessor());
    }
}
