package org.endeavourhealth.cim.camel.processors.clinical;

import org.apache.http.HttpStatus;
import org.endeavourhealth.cim.camel.exceptions.BusinessRuleConflictException;
import org.endeavourhealth.cim.camel.exceptions.BusinessRuleException;
import org.endeavourhealth.cim.camel.exceptions.NotFoundException;
import org.endeavourhealth.cim.camel.exceptions.UnexpectedException;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;

import java.util.UUID;

public class AddConditionProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
        UUID patientId = ExchangeHelper.getInHeaderUUID(exchange, CIMHeaderKey.Id, true);
        String requestBody = ExchangeHelper.getInBodyString(exchange);

        IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
        String responseBody = dataManager.addCondition(odsCode, patientId, requestBody);

        if (responseBody.equals("PatientNotFound"))
            throw new NotFoundException("Patient not found");

        if (responseBody.equals("IdentifierAlreadyInUse"))
            throw new BusinessRuleConflictException("Identifier in use");

        if (!responseBody.equals("Successful"))
            throw new UnexpectedException("Unexpected response from principal system:\r\n\r\n" + responseBody);

        ExchangeHelper.setInBodyString(exchange, "");
        ExchangeHelper.setInHeader(exchange, Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_CREATED);
    }
}
