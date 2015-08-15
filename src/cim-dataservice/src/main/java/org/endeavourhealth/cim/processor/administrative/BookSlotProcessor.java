package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.ParametersHelper;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.processor.CIMProcessor;
import org.hl7.fhir.instance.model.Parameters;

import java.util.UUID;

public class BookSlotProcessor extends CIMProcessor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = getInHeaderString(exchange, HeaderKey.OdsCode);
        String slotId = getInHeaderString(exchange, HeaderKey.Id);
        Parameters parameters = getInBodyResource(exchange, Parameters.class);

        UUID patientId = UUID.fromString(ParametersHelper.getStringParameter(parameters, "patient"));

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        dataManager.bookSlot(odsCode, slotId, patientId);

        setInBodyString(exchange, "");
    }
}
