package org.endeavourhealth.cim.camel.processors.demographics;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.emis.DataManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetChangedPatientsProcessor implements org.apache.camel.Processor {
    private final DataManager _dataManager;
    private Date _lastPoll = null;

    public GetChangedPatientsProcessor(String odsCode) throws Exception {
        _dataManager = new DataManager();
    }

    public GetChangedPatientsProcessor(DataManager dataManager) {
        _dataManager = dataManager;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode);
        String lastUpdate = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.LastUpdated);

        Date dateUpdated = null;

        if (lastUpdate != null)         // Call originated from api
            dateUpdated = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(lastUpdate.substring(1));
        else {
            dateUpdated = _lastPoll;    // Call originated from subscription
            _lastPoll = new Date();
            if (dateUpdated == null) {  // Baseline poll - do nothing (option for full sync on first poll?)
                exchange.getIn().setBody(new ArrayList<>());
                return;
            }
        }

        try {
            List<String> changedPatientIds = _dataManager.getChangedPatientIds(odsCode, dateUpdated);
            exchange.getIn().setBody(changedPatientIds);
        } catch (Exception e) {
            // Rollback poll date as this poll failed
            if (_lastPoll != null)
                _lastPoll = dateUpdated;
        }
    }
}

