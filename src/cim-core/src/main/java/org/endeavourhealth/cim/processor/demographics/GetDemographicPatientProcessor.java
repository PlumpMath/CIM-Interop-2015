package org.endeavourhealth.cim.processor.demographics;

import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.exceptions.NotFoundException;
import org.endeavourhealth.core.utils.TextUtils;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;

public class GetDemographicPatientProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.DestinationOdsCode, true);
        String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String requestBody = dataManager.getPatientDemographics(odsCode, patientId);

        if (TextUtils.isNullOrTrimmedEmpty(requestBody))
            throw new NotFoundException("Patient not found");

        ExchangeHelper.setInBodyString(exchange, requestBody);
    }
}
