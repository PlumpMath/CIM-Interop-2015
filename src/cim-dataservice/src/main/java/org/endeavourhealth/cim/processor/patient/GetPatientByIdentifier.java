package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class GetPatientByIdentifier implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        // Get data from exchange
        String nhsNumber = (String)exchange.getIn().getHeader("identifier");
        nhsNumber = nhsNumber.substring(4);
        String odsCode = (String) exchange.getIn().getHeader("odsCode");

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);

        // Get patient data by NHS Number
        String body = dataManager.getPatientDemographicsByNHSNumber(odsCode, nhsNumber);

        exchange.getIn().setBody(body);
    }
}
