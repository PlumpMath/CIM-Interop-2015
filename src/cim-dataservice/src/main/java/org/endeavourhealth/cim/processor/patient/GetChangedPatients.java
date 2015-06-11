package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class GetChangedPatients implements org.apache.camel.Processor {
    private final IDataAdapter _dataAdapter;
    private Date _lastPoll = null;

    public GetChangedPatients(String odsCode) throws Exception {
        _dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
    }

    public GetChangedPatients(IDataAdapter dataAdapter) {
        _dataAdapter = dataAdapter;
    }

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

        try {
            ArrayList<UUID> changedPatientIds = _dataAdapter.getChangedPatients(odsCode, dateUpdated);
            exchange.getIn().setBody(changedPatientIds);
        } catch (Exception e) {
            // Rollback poll date as this poll failed
            if (_lastPoll != null)
                _lastPoll = dateUpdated;
        }
    }
}

