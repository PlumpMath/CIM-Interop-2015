package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.common.text.TextUtils;
import org.endeavourhealth.cim.transform.exceptions.SerializationException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotStruct;
import org.hl7.fhir.instance.model.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class SlotTransformer {

    public static ArrayList<Resource> transformToSlotResources(String scheduleId, SlotListStruct appointmentSlotList) throws SerializationException, TransformFeatureNotSupportedException {
        ArrayList<Resource> resources = new ArrayList<Resource>();

        for (SlotStruct appointmentSlot : appointmentSlotList.getSlot())
            resources.add(transformToSlot(appointmentSlot, scheduleId));

        return resources;
    }

    private static Slot transformToSlot(SlotStruct appointmentSlot, String scheduleId) throws SerializationException, TransformFeatureNotSupportedException {
        Slot slot = new Slot();
        slot.setId(Integer.toString(appointmentSlot.getDBID()));

        Time startTime = EmisOpenCommon.getTime(appointmentSlot.getStartTime());
        Time endTime = EmisOpenCommon.addMinutesToTime(startTime, Integer.parseInt(appointmentSlot.getSlotLength()));

        Date startDate = EmisOpenCommon.getDateAndTime(appointmentSlot.getDate(), appointmentSlot.getStartTime());
        Date endDate = EmisOpenCommon.getDateAndTime(appointmentSlot.getDate(), endTime.toString());

        slot.setStart(startDate);
        slot.setEnd(endDate);

        String slotStatus = appointmentSlot.getStatus();

        if (!TextUtils.isNullOrTrimmedEmpty(slotStatus))
            slot.setFreeBusyType(getSlotStatus(slotStatus));

        slot.setSchedule(ReferenceHelper.createReference(ResourceType.Schedule, scheduleId));

        return slot;
    }

    private static Slot.Slotstatus getSlotStatus(String slotStatus) throws TransformFeatureNotSupportedException {
        switch (slotStatus) {
            case "Slot Available":
                return Slot.Slotstatus.FREE;
            case "Arrived":
            case "Send In":
            case "Left":
            case "DNA":
            case "Walked Out":
            case "Visited":
            case "Visited - Not In":
            case "Telephone - Complete":
            case "Telephone - Not In":
            case "Quiet Send In":
            case "Start Call":
            case "Cannot Be Seen":
            case "Booked":
                return Slot.Slotstatus.BUSY;
            case "Blocked":
            case "Embargoed":
                return Slot.Slotstatus.BUSYUNAVAILABLE;
            case "Unknown":
                return Slot.Slotstatus.BUSYTENTATIVE;
            default:
                throw new TransformFeatureNotSupportedException("SlotStatus not supported: " + slotStatus);
        }
    }
}
