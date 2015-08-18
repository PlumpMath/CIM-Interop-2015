package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.ParametersHelper;
import org.endeavourhealth.cim.common.exceptions.*;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.hl7.fhir.instance.model.Parameters;

import java.util.UUID;

public class CancelSlotProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode, true);
        String slotId = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Id, true);
        Parameters parameters = ExchangeHelper.getInBodyResource(exchange, Parameters.class, true);

        UUID patientId = null;

        try {
            patientId = ParametersHelper.getUUIDParameter(parameters, "patient");
        } catch (Exception e) {
            throw new CIMInvalidParamException(e);
        }

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String cancelSlotResponse = dataManager.cancelSlot(odsCode, slotId, patientId);

        if (cancelSlotResponse.equals("SlotNotFound"))
            throw new CIMNotFoundException("Slot not found");

        if (cancelSlotResponse.equals("PatientNotFound"))
            throw new CIMNotFoundException("Patient not found");

        if (cancelSlotResponse.equals("SlotNotBookedByThisPatient"))
            throw new CIMBusinessRuleException("Slot not booked by this patient");

        if (!cancelSlotResponse.equals("Successful"))
            throw new CIMUnexpectedException("Unexpected response from principal system:\r\n\r\n" + cancelSlotResponse);

        ExchangeHelper.setInBodyString(exchange, "");
    }
}
