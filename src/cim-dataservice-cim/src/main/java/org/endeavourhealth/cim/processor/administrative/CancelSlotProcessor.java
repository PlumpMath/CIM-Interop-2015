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

public class CancelSlotProcessor implements Processor {

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
