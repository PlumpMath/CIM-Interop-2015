package org.endeavourhealth.cim.camel.routes.domains;

import org.endeavourhealth.cim.camel.processors.appointments.BookSlotProcessor;
import org.endeavourhealth.cim.camel.processors.appointments.CancelSlotProcessor;
import org.endeavourhealth.cim.camel.processors.appointments.GetSlotsProcessor;
import org.endeavourhealth.cim.camel.routes.common.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SlotRoutes extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String GET_SLOTS_ROUTE = "GetSlots";
        final String BOOK_SLOT_ROUTE = "BookSlot";
        final String CANCEL_SLOT_ROUTE = "CancelSlot";

        final String BASE_PATH = "/{odsCode}/Slot";
        final String GET_SLOTS_URI = "?schedule={scheduleId}";
        final String BOOK_SLOT_URI = "/{id}/$book";
        final String CANCEL_SLOT_URI = "/{id}/$cancel";

        // endpoints
        rest(BASE_PATH)

        .get(GET_SLOTS_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + GET_SLOTS_URI)
            .to(BaseRouteBuilder.direct(GET_SLOTS_ROUTE))
        .endRest()

        .post(BOOK_SLOT_URI)
            .route()
            .routeId(HttpVerb.POST + BASE_PATH + BOOK_SLOT_URI)
            .to(BaseRouteBuilder.direct(BOOK_SLOT_ROUTE))
        .endRest()

        .post(CANCEL_SLOT_URI)
            .route()
            .routeId(HttpVerb.POST + BASE_PATH + CANCEL_SLOT_URI)
            .to(BaseRouteBuilder.direct(CANCEL_SLOT_ROUTE))
        .endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_SLOTS_ROUTE)
                .process(new GetSlotsProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, BOOK_SLOT_ROUTE)
                .process(new BookSlotProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, CANCEL_SLOT_ROUTE)
                .process(new CancelSlotProcessor());
    }
}
