package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.slots.BookSlotProcessor;
import org.endeavourhealth.cim.processor.slots.CancelSlotProcessor;
import org.endeavourhealth.cim.processor.slots.GetSlotsProcessor;

@SuppressWarnings("WeakerAccess")
public class SlotEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("{odsCode}/Slot")
            .description("Slot rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get("?schedule={scheduleId}&start={start}")
            .route()
            .routeId("GetScheduleAvailableSlots")
            .description("Get slots in a given schedule")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetSlots"))
            .to("direct:CIMCore")
        .endRest()

        .post("/{id}/$book")
            .route()
            .routeId("BookSlotForPatient")
            .description("Book slot")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:BookSlot"))
            .to("direct:CIMCore")
        .endRest()

        .post("/{id}/$cancel")
                .route()
                .routeId("CancelSlotForPatient")
                .description("Cancel slot")
                .setHeader(HeaderKey.MessageRouterCallback, constant("direct:CancelSlot"))
                .to("direct:CIMCore")
                .endRest();

        // Message router callback routes
        from("direct:GetSlots")
            .routeId("GetSlots")
            .process(new GetSlotsProcessor());

        from("direct:BookSlot")
            .routeId("BookSlot")
            .process(new BookSlotProcessor());

        from("direct:CancelSlot")
            .routeId("CancelSlot")
            .process(new CancelSlotProcessor());
    }
}
