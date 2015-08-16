package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.processor.administrative.BookSlotProcessor;
import org.endeavourhealth.cim.processor.administrative.CancelSlotProcessor;
import org.endeavourhealth.cim.processor.administrative.GetSlotsProcessor;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SlotEndpoint extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Slot";
        final String GET_SLOTS_ROUTE = "?schedule={scheduleId}&start={start}";
        final String BOOK_ROUTE = "/{id}/$book";
        final String CANCEL_ROUTE = "/{id}/$cancel";

        final String GET_SLOTS_PROCESSOR_ROUTE = "GetSlots";
        final String BOOK_PROCESSOR_ROUTE = "BookSlot";
        final String CANCEL_PROCESSOR_ROUTE = "CancelSlot";

        rest(BASE_ROUTE)

        .get(GET_SLOTS_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + GET_SLOTS_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(GET_SLOTS_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

        .post(BOOK_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + BOOK_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(BOOK_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

        .post(CANCEL_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + CANCEL_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(CANCEL_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(GET_SLOTS_PROCESSOR_ROUTE))
            .routeId(GET_SLOTS_PROCESSOR_ROUTE)
            .process(new GetSlotsProcessor());

        from(Route.direct(BOOK_PROCESSOR_ROUTE))
            .routeId(BOOK_PROCESSOR_ROUTE)
            .process(new BookSlotProcessor());

        from(Route.direct(CANCEL_PROCESSOR_ROUTE))
            .routeId(CANCEL_PROCESSOR_ROUTE)
            .process(new CancelSlotProcessor());
    }
}
