package org.endeavourhealth.cim.processor.clinical;

import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.exceptions.NotFoundException;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;

public class GetFullPatientRecordProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode, true);
        String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String response = dataManager.getFullRecord(odsCode, patientId);

        if (response == null)
            throw new NotFoundException("patient");

        ExchangeHelper.setInBodyString(exchange, response);
    }
}
