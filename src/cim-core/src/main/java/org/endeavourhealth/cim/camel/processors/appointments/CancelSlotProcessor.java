package org.endeavourhealth.cim.camel.processors.appointments;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.helpers.ParametersHelper;
import org.endeavourhealth.cim.camel.exceptions.*;
import org.endeavourhealth.cim.dataManager.emis.DataManager;
import org.hl7.fhir.instance.model.Parameters;

public class CancelSlotProcessor implements Processor {

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
        String cancelSlotResponse = dataManager.cancelSlot(odsCode, slotId, patientId);

        if (cancelSlotResponse.equals("SlotNotFound"))
            throw new NotFoundException("Slot not found");

        if (cancelSlotResponse.equals("PatientNotFound"))
            throw new NotFoundException("Patient not found");

        if (cancelSlotResponse.equals("SlotNotBookedByThisPatient"))
            throw new BusinessRuleException("Slot not booked by this patient");

        if (!cancelSlotResponse.equals("Successful"))
            throw new UnexpectedException("Unexpected response from principal system:\r\n\r\n" + cancelSlotResponse);

        ExchangeHelper.setInBodyString(exchange, "");
    }
}
