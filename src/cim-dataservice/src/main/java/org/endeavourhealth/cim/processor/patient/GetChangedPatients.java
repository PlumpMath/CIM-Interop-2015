package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class GetChangedPatients implements org.apache.camel.Processor {
    private Date _lastPoll = null;
    @Override
    public void process(Exchange exchange) throws Exception {
        String odsCode = (String)exchange.getIn().getHeader("odsCode");
        String lastUpdate = (String)exchange.getIn().getHeader("_lastUpdated");

        Date dateUpdated = null;

        if (lastUpdate != null)
            dateUpdated = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(lastUpdate.substring(1));
        else {
            dateUpdated = _lastPoll;
            _lastPoll = new Date();
            if (dateUpdated == null)
                return;
        }

        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
        ArrayList<UUID> changedPatientIds = dataAdapter.getChangedPatients(dateUpdated);
        exchange.getIn().setBody(changedPatientIds);
    }
}

