package org.endeavourhealth.cim.camel.processors.demographics;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.exceptions.NotFoundException;
import org.endeavourhealth.cim.dataManager.emis.DataManager;
import org.endeavourhealth.cim.repository.utils.TextUtils;

public class GetDemographicPatientProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
        String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

        DataManager dataManager = new DataManager();
        String requestBody = dataManager.getPatientDemographics(odsCode, patientId);

        if (TextUtils.isNullOrTrimmedEmpty(requestBody))
            throw new NotFoundException("Patient not found");

        ExchangeHelper.setInBodyString(exchange, requestBody);
    }
}
