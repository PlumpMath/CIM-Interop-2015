package org.endeavourhealth.cim.processor.clinical;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.UUID;

public class GetFullPatientRecordProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        UUID patientId = ExchangeHelper.getInHeaderUUID(exchange, HeaderKey.Id);
        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String response = dataManager.getPatientRecord(odsCode, patientId);

        ExchangeHelper.setInBodyString(exchange, response);
    }
}
