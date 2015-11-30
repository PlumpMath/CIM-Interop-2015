package org.endeavourhealth.cim.processor.administrative;

import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.ParametersHelper;
import org.endeavourhealth.common.core.exceptions.*;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.hl7.fhir.instance.model.Parameters;

public class BookSlotProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode, true);
        String slotId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);
        Parameters parameters = ExchangeHelper.getInBodyResource(exchange, Parameters.class, true);

        String patientId = null;

        try {
            patientId = ParametersHelper.getStringParameter(parameters, "patient");
        } catch (Exception e) {
            throw new InvalidParamException(e);
        }

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
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
