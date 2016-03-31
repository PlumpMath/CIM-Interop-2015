package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.appointments.BookSlotProcessor;
import org.endeavourhealth.cim.camel.processors.appointments.CancelSlotProcessor;
import org.endeavourhealth.cim.camel.processors.appointments.GetSlotsProcessor;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SlotRoutes extends BaseRouteBuilder {
	public static final String GET_SLOTS_ROUTE = "GetSlots";
	public static final String BOOK_SLOT_ROUTE = "BookSlot";
	public static final String CANCEL_SLOT_ROUTE = "CancelSlot";

    @Override
    public void configureRoute() throws Exception {
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_SLOTS_ROUTE)
            .process(new GetSlotsProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, BOOK_SLOT_ROUTE)
            .process(new BookSlotProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, CANCEL_SLOT_ROUTE)
            .process(new CancelSlotProcessor());
    }
}
