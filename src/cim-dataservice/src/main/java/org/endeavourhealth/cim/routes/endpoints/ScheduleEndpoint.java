package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.schedules.GetSchedulesProcessor;

@SuppressWarnings("WeakerAccess")
public class ScheduleEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("{odsCode}/Schedule?date={date}&actor={actor}")
            .description("Schedule rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get()
            .route()
            .routeId("GetAvailableAppointmentSchedules")
            .description("Get available appointment schedules")
            .setHeader("MessageRouterCallback", constant("direct:GetSchedules"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetSchedules")
            .routeId("GetSchedules")
            .process(new GetSchedulesProcessor());
    }
}
