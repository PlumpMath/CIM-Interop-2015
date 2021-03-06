package org.endeavourhealth.cim.camel.processors.clinical;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.exceptions.NotFoundException;

import java.util.UUID;

public class GetFullRecordProcessor implements Processor
{
    public void process(Exchange exchange) throws Exception
    {
        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
        UUID patientId = ExchangeHelper.getInHeaderUUID(exchange, CIMHeaderKey.Id, true);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String response = dataManager.getFullRecord(odsCode, patientId);

        if (response == null)
            throw new NotFoundException("patient");

        ExchangeHelper.setInBodyString(exchange, response);
    }
}
