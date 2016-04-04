package org.endeavourhealth.cim.camel.routes.domains;

import org.endeavourhealth.cim.camel.processors.appointments.BookSlotProcessor;
import org.endeavourhealth.cim.camel.processors.appointments.CancelSlotProcessor;
import org.endeavourhealth.cim.camel.processors.appointments.SearchSlotsProcessor;
import org.endeavourhealth.cim.camel.routes.common.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SlotRoutes extends BaseRouteBuilder
{
    @Override
    public void configureRoute() throws Exception
    {
        final String SEARCH_SLOTS_ROUTE = "SearchSlots";
        final String BOOK_SLOT_ROUTE = "BookSlot";
        final String CANCEL_SLOT_ROUTE = "CancelSlot";

        final String BASE_PATH = "/{odsCode}/Slot";
        final String SEARCH_SLOTS_URI = "/$search?date={date}&actor:Practitioner={practitioner}";
        final String BOOK_SLOT_URI = "/{id}/$book";
        final String CANCEL_SLOT_URI = "/{id}/$cancel";

        // endpoints
        rest(BASE_PATH)

        .get(SEARCH_SLOTS_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + SEARCH_SLOTS_URI)
            .to(BaseRouteBuilder.direct(SEARCH_SLOTS_ROUTE))
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
        buildWrappedRoute(CimCore.ROUTE_NAME, SEARCH_SLOTS_ROUTE)
                .process(new SearchSlotsProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, BOOK_SLOT_ROUTE)
                .process(new BookSlotProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, CANCEL_SLOT_ROUTE)
                .process(new CancelSlotProcessor());
    }
}
