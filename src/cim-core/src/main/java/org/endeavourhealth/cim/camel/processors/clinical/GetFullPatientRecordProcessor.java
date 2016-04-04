package org.endeavourhealth.cim.camel.processors.clinical;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.exceptions.NotFoundException;
import org.endeavourhealth.cim.dataManager.emis.DataManager;

public class GetFullPatientRecordProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
        String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

        DataManager dataManager = new DataManager();
        String response = dataManager.getFullRecord(odsCode, patientId);

        if (response == null)
            throw new NotFoundException("patient");

        ExchangeHelper.setInBodyString(exchange, response);
    }
}
