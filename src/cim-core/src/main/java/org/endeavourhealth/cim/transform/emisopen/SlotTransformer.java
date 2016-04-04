package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotStruct;
import org.hl7.fhir.instance.model.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SlotTransformer
{
    public static List<Slot> transform(SlotListStruct appointmentSlotList) throws TransformException
    {
        ArrayList<Slot> fhirSlots = new ArrayList<>();

        for (SlotStruct appointmentSlot : appointmentSlotList.getSlot())
            fhirSlots.add(transform(appointmentSlot));

        return fhirSlots;
    }

    private static Slot transform(SlotStruct appointmentSlot) throws TransformException
    {
        Slot slot = new Slot();

        slot.setId(appointmentSlot.getGUID());
        slot.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_SLOT));

        slot.setSchedule(ReferenceHelper.createReference(ResourceType.Schedule, appointmentSlot.getSessionGUID()));

        String slotStatus = appointmentSlot.getStatus();

        if (!TextUtils.isNullOrTrimmedEmpty(slotStatus))
            slot.setFreeBusyType(getSlotStatus(slotStatus));

        Time startTime = EmisOpenCommon.getTime(appointmentSlot.getStartTime());
        Time endTime = EmisOpenCommon.addMinutesToTime(startTime, Integer.parseInt(appointmentSlot.getSlotLength()));

        Date startDate = EmisOpenCommon.getDateAndTime(appointmentSlot.getDate(), appointmentSlot.getStartTime());
        Date endDate = EmisOpenCommon.getDateAndTime(appointmentSlot.getDate(), endTime.toString());

        slot.setStart(startDate);
        slot.setEnd(endDate);

        return slot;
    }

    private static Slot.SlotStatus getSlotStatus(String slotStatus) throws TransformException
    {
        switch (slotStatus)
        {
            case "Slot Available":
                return Slot.SlotStatus.FREE;
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
                return Slot.SlotStatus.BUSY;
            case "Blocked":
            case "Embargoed":
                return Slot.SlotStatus.BUSYUNAVAILABLE;
            case "Unknown":
                return Slot.SlotStatus.BUSYTENTATIVE;
            default:
                throw new TransformFeatureNotSupportedException("SlotStatus not supported: " + slotStatus);
        }
    }
}
