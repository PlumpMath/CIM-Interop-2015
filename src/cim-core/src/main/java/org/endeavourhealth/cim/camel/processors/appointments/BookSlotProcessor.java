package org.endeavourhealth.cim.camel.processors.appointments;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.helpers.ParametersHelper;
import org.endeavourhealth.cim.camel.exceptions.*;
import org.endeavourhealth.cim.dataManager.emis.DataManager;
import org.hl7.fhir.instance.model.Parameters;

public class BookSlotProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
        String slotId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);
        Parameters parameters = ExchangeHelper.getInBodyResource(exchange, Parameters.class, true);

        String patientId = null;

        try {
            patientId = ParametersHelper.getStringParameter(parameters, "patient");
        } catch (Exception e) {
            throw new InvalidParamException(e);
        }

        DataManager dataManager = new DataManager();
        String bookSlotResponse = dataManager.bookSlot(odsCode, slotId, patientId);

        if (bookSlotResponse.equals("SlotNotFound"))
            throw new NotFoundException("Slot not found");

        if (bookSlotResponse.equals("PatientNotFound"))
            throw new NotFoundException("Patient not found");

        if (bookSlotResponse.equals("SlotAlreadyBooked"))
            throw new BusinessRuleConflictException("Slot already booked");

        if (bookSlotResponse.equals("SlotAlreadyBookedByThisPatient"))
            throw new BusinessRuleException("Slot already booked by this patient");

        if (!bookSlotResponse.equals("Successful"))
            throw new UnexpectedException("Unexpected response from principal system:\r\n\r\n" + bookSlotResponse);

        ExchangeHelper.setInBodyString(exchange, "");
    }
}
