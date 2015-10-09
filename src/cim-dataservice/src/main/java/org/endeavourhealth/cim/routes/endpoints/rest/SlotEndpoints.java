package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.SlotRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SlotEndpoints extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Slot";

        final String GET_SLOTS_URI = "?schedule={scheduleId}";
        final String BOOK_SLOT_URI = "/{id}/$book";
        final String CANCEL_SLOT_URI = "/{id}/$cancel";

        rest(BASE_PATH)

        .get(GET_SLOTS_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + GET_SLOTS_URI)
            .to(Route.direct(SlotRoutes.GET_SLOTS_ROUTE))
        .endRest()

        .post(BOOK_SLOT_URI)
            .route()
            .routeId(HttpVerb.POST + BASE_PATH + BOOK_SLOT_URI)
            .to(Route.direct(SlotRoutes.BOOK_SLOT_ROUTE))
        .endRest()

        .post(CANCEL_SLOT_URI)
            .route()
            .routeId(HttpVerb.POST + BASE_PATH + CANCEL_SLOT_URI)
            .to(Route.direct(SlotRoutes.CANCEL_SLOT_ROUTE))
        .endRest();
    }
}
