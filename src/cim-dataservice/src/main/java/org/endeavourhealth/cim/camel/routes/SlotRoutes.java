package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.administrative.BookSlotProcessor;
import org.endeavourhealth.cim.processor.administrative.CancelSlotProcessor;
import org.endeavourhealth.cim.processor.administrative.GetSlotsProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SlotRoutes extends BaseRouteBuilder {
	public static final String GET_SLOTS_ROUTE = "GetSlots";
	public static final String BOOK_SLOT_ROUTE = "BookSlot";
	public static final String CANCEL_SLOT_ROUTE = "CancelSlot";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(CimCore.ROUTE_NAME, GET_SLOTS_ROUTE)
            .process(new GetSlotsProcessor());

        buildCallbackRoute(CimCore.ROUTE_NAME, BOOK_SLOT_ROUTE)
            .process(new BookSlotProcessor());

        buildCallbackRoute(CimCore.ROUTE_NAME, CANCEL_SLOT_ROUTE)
            .process(new CancelSlotProcessor());
    }
}
