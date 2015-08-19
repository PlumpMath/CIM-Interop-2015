package org.endeavourhealth.cim.processor.clinical;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.exceptions.CIMNotFoundException;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.UUID;

public class GetFullPatientRecordProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode, true);
        UUID patientId = ExchangeHelper.getInHeaderUUID(exchange, HeaderKey.Id, true);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String response = dataManager.getFullRecord(odsCode, patientId);

        if (response == null)
            throw new CIMNotFoundException("patient");

        ExchangeHelper.setInBodyString(exchange, response);
    }
}
