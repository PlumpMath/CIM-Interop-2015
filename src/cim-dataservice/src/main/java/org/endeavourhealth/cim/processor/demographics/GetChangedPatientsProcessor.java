package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.common.core.HeaderKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetChangedPatientsProcessor implements org.apache.camel.Processor {
    private final IDataManager _dataManager;
    private Date _lastPoll = null;

    public GetChangedPatientsProcessor(String odsCode) throws Exception {
        _dataManager = DataManagerFactory.getDataManagerForService(odsCode);
    }

    public GetChangedPatientsProcessor(IDataManager dataManager) {
        _dataManager = dataManager;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
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
            List<String> changedPatientIds = _dataManager.getChangedPatients(odsCode, dateUpdated);
            exchange.getIn().setBody(changedPatientIds);
        } catch (Exception e) {
            // Rollback poll date as this poll failed
            if (_lastPoll != null)
                _lastPoll = dateUpdated;
        }
    }
}

