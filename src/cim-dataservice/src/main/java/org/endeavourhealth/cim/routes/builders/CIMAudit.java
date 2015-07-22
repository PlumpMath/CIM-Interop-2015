package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.processor.core.CqlAuditParamsProcessor;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CIMAudit extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMAudit")
            .routeId("CIMAudit")
            .process(new CqlAuditParamsProcessor())
            .to("cql://endeavour-cim.cloudapp.net/CIM?username=" + DataConfiguration.getUsername() + "&password=" + DataConfiguration.getPassword() + "&cql=insert into Audit (rowkey, message) values (?, ?)");
    }
}
