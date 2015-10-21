package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.*;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.exceptions.InvalidParamException;
import org.endeavourhealth.common.core.searchParameters.TokenSearchParameter;
import org.endeavourhealth.core.text.TextUtils;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.transform.common.FhirConstants;

import java.util.Date;

public class TracePersonProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

		TokenSearchParameter identifier = ExchangeHelper.getInHeaderToken(exchange, CIMHeaderKey.Identifier);
		String name = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Name);
		Date dateOfBirth = ExchangeHelper.getInHeaderDate(exchange, CIMHeaderKey.BirthDate);
		String gender = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Gender);
		IDataManager dataManager = (IDataManager)ExchangeHelper.getInBodyObject(exchange);

		String responseBody;

		if (identifier != null) {

			if ((!TextUtils.isNullOrTrimmedEmpty(name)) || (!TextUtils.isNullOrTrimmedEmpty(gender)) || (dateOfBirth != null))
				throw new InvalidParamException("Invalid parameter combination");

			if (!TextUtils.isNullOrTrimmedEmpty(identifier.getSystem()))
				if (!identifier.getSystem().equals(FhirConstants.CODE_SYSTEM_NHSNUMBER))
					throw new InvalidParamException("Identifier code system not recognised");

			if (!NhsNumberValidator.IsValidNhsNumber(identifier.getCode()))
				throw new InvalidParamException("NHS number is not valid");

			responseBody = dataManager.tracePersonByNhsNumber(identifier.getCode());
		}
		else {
			if ((TextUtils.isNullOrTrimmedEmpty(name)) || (TextUtils.isNullOrTrimmedEmpty(gender)) || (dateOfBirth == null))
				throw new InvalidParamException("Invalid parameter combination");

			responseBody = dataManager.tracePersonByDemographics(name, dateOfBirth, gender, "", "");
		}

		ExchangeHelper.setOutBodyString(exchange, responseBody);
    }
}
