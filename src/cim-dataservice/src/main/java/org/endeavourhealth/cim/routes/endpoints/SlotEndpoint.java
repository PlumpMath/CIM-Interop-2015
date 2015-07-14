package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.slots.GetSlotsProcessor;

@SuppressWarnings("WeakerAccess")
public class SlotEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("{odsCode}/Slot?schedule={scheduleId}&start={start}")
            .description("Slot rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get()
            .route()
            .routeId("GetScheduleAvailableSlots")
            .description("Get slots in a given schedule")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetSlots"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetSlots")
            .routeId("GetSlots")
            .process(new GetSlotsProcessor());
    }
}
