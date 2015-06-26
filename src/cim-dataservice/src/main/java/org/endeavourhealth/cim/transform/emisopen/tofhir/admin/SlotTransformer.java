package org.endeavourhealth.cim.transform.emisopen.tofhir.admin;

import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotStruct;
import org.hl7.fhir.instance.model.Resource;
import org.hl7.fhir.instance.model.Slot;

import java.util.ArrayList;

public class SlotTransformer
{
    public static ArrayList<Resource> transformToSlotResources(SlotListStruct appointmentSlotList)
    {
        ArrayList<Resource> resources = new ArrayList<Resource>();

        for (SlotStruct appointmentSlot : appointmentSlotList.getSlot())
            resources.add(transformToSlot(appointmentSlot));

        return resources;
    }

    private static Slot transformToSlot(SlotStruct appointmentSlot)
    {
        Slot slot = new Slot();
        slot.setId(Integer.toString(appointmentSlot.getDBID()));
        return slot;
    }
}
