package org.endeavourhealth.cim.camel.processors.demographics;

import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.exceptions.NotFoundException;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;

public class GetDemographicPatientProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
        String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String requestBody = dataManager.getPatientDemographics(odsCode, patientId);

        if (TextUtils.isNullOrTrimmedEmpty(requestBody))
            throw new NotFoundException("Patient not found");

        ExchangeHelper.setInBodyString(exchange, requestBody);
    }
}
