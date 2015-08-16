package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.ParametersHelper;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.hl7.fhir.instance.model.Parameters;

import java.util.UUID;

public class CancelSlotProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
        String slotId = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Id);
        Parameters parameters = ExchangeHelper.getInBodyResource(exchange, Parameters.class);

        UUID patientId = ParametersHelper.getUUIDParameter(parameters, "patient");

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        dataManager.cancelSlot(odsCode, slotId, patientId);

        ExchangeHelper.setInBodyString(exchange, "");
    }
}