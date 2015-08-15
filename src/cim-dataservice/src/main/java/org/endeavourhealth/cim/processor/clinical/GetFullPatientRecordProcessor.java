package org.endeavourhealth.cim.processor.clinical;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.processor.CIMProcessor;

import java.util.UUID;

public class GetFullPatientRecordProcessor extends CIMProcessor {

    public void process(Exchange exchange) throws Exception {

        UUID patientId = getInHeaderUUID(exchange, HeaderKey.Id);
        String odsCode = getInHeaderString(exchange, HeaderKey.OdsCode);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String response = dataManager.getPatientRecordByPatientId(odsCode, patientId);

        setInBodyString(exchange, response);
    }
}
