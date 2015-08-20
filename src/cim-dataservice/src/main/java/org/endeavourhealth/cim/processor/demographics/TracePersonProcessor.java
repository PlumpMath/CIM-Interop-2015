package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.*;
import org.endeavourhealth.cim.common.exceptions.CIMBusinessRuleException;
import org.endeavourhealth.cim.common.exceptions.CIMInvalidParamException;
import org.endeavourhealth.cim.common.exceptions.CIMValidationRuleException;
import org.endeavourhealth.cim.common.text.TextUtils;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TracePersonProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

		TokenSearchParameter identifier = ExchangeHelper.getInHeaderToken(exchange, HeaderKey.Identifier);
		String name = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Name);
		Date dateOfBirth = ExchangeHelper.getInHeaderDate(exchange, HeaderKey.BirthDate);
		String gender = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Gender);
		IDataManager dataManager = (IDataManager)ExchangeHelper.getInBodyObject(exchange);

		String responseBody;

		if (identifier != null) {

			if ((!TextUtils.isNullOrTrimmedEmpty(name)) || (!TextUtils.isNullOrTrimmedEmpty(gender)) || (dateOfBirth != null))
				throw new CIMInvalidParamException("Invalid parameter combination");

			if (!TextUtils.isNullOrTrimmedEmpty(identifier.getSystem()))
				if (!identifier.getSystem().equals(FhirConstants.CODE_SYSTEM_NHSNUMBER))
					throw new CIMInvalidParamException("Identifier code system not recognised");

			if (!NhsNumberValidator.IsValidNhsNumber(identifier.getCode()))
				throw new CIMInvalidParamException("NHS number is not valid");

			responseBody = dataManager.tracePatientByNhsNumber(identifier.getCode());
		}
		else {
			if ((TextUtils.isNullOrTrimmedEmpty(name)) || (TextUtils.isNullOrTrimmedEmpty(gender)) || (dateOfBirth == null))
				throw new CIMInvalidParamException("Invalid parameter combination");

			responseBody = dataManager.tracePatientByDemographics(name, dateOfBirth, gender, "", "");
		}

		ExchangeHelper.setOutBodyString(exchange, responseBody);
    }
}
