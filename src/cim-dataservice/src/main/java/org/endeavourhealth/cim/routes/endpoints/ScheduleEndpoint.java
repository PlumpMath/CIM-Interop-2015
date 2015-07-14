package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.schedules.GetSchedulesProcessor;

@SuppressWarnings("WeakerAccess")
public class ScheduleEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("{odsCode}/Schedule?date={date}&actor:Practitioner={practitioner}")
            .description("Schedule rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get()
            .route()
            .routeId("GetAvailableAppointmentSchedules")
            .description("Get available appointment schedules")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetSchedules"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetSchedules")
            .routeId("GetSchedules")
            .doTry()
                .process(new GetSchedulesProcessor())
            .doCatch(IllegalArgumentException.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("${exception.message}")))
                .to("direct:CIMInvalidMessage")
            .end();

    }
}
