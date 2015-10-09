package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.BookSlotProcessor;
import org.endeavourhealth.cim.processor.administrative.CancelSlotProcessor;
import org.endeavourhealth.cim.processor.administrative.GetSlotsProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SlotRoutes extends CIMRouteBuilder {
	public static final String GET_SLOTS_ROUTE = "GetSlots";
	public static final String BOOK_SLOT_ROUTE = "BookSlot";
	public static final String CANCEL_SLOT_ROUTE = "CancelSlot";

    @Override
    public void configureRoute() throws Exception {
        buildCimCallbackRoute(GET_SLOTS_ROUTE)
            .process(new GetSlotsProcessor());

        buildCimCallbackRoute(BOOK_SLOT_ROUTE)
            .process(new BookSlotProcessor());

        buildCimCallbackRoute(CANCEL_SLOT_ROUTE)
            .process(new CancelSlotProcessor());
    }
}
