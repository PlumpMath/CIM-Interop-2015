package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.administrative.GetSchedulesProcessor;

@SuppressWarnings("WeakerAccess")
public class ScheduleEndpoint extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Schedule";
        final String GET_SCHEDULES_ROUTE = "?date={date}&actor:Practitioner={practitioner}";

        final String GET_SCHEDULES_PROCESSOR_ROUTE = "GetSchedules";

        rest(BASE_ROUTE)

        .get(GET_SCHEDULES_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + GET_SCHEDULES_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(GET_SCHEDULES_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(GET_SCHEDULES_PROCESSOR_ROUTE))
            .routeId(GET_SCHEDULES_PROCESSOR_ROUTE)
            .doTry()
                .process(new GetSchedulesProcessor())
            .doCatch(IllegalArgumentException.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("${exception.message}")))
                .to(Route.direct(CoreRouteName.CIM_INVALID_MESSAGE))
            .end();

    }
}
