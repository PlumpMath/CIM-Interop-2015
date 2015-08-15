package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.exceptions.LegitimateRelationshipException;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.core.DataProtocolProcessor;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("unused")
public class CIMDataProtocol extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL))
            .routeId(CoreRouteName.CIM_DATA_PROTOCOL)
            .doTry()
                .process(new DataProtocolProcessor())
            .doCatch(LegitimateRelationshipException.class)
                .process(new CIMError(HttpStatus.SC_FORBIDDEN, simple("${exception.message}")))
			    .to(Route.direct(CoreRouteName.CIM_INVALID_MESSAGE))
            .end()
            .to(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL_RESULT));
    }
}
