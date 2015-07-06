package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.UUID;

public class GetPatientRecordByPatientId implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        String patientId = (String)exchange.getIn().getHeader("id");
        String odsCode = (String) exchange.getIn().getHeader("odsCode");

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String body = dataManager.getPatientRecordByPatientId(odsCode, UUID.fromString(patientId));

        exchange.getIn().setBody(body);
    }
}
