package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class GetChangedPatients implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String odsCode = (String)exchange.getIn().getHeader("odsCode");
        Date dateUpdated = new SimpleDateFormat().parse(((String)exchange.getIn().getHeader("_lastUpdated")).substring(1));
        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
        ArrayList<UUID> changedPatientIds = dataAdapter.getChangedPatients(dateUpdated);
        exchange.getIn().setBody(changedPatientIds);
    }
}
