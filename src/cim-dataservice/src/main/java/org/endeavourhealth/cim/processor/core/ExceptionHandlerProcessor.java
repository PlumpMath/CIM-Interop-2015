package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.FhirIssueType;
import org.endeavourhealth.cim.common.exceptions.CIMException;
import org.endeavourhealth.cim.common.text.TextUtils;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.CodeableConcept;
import org.hl7.fhir.instance.model.OperationOutcome;

public class ExceptionHandlerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        int httpStatus = HttpStatus.SC_INTERNAL_SERVER_ERROR;

        if (CIMException.class.isAssignableFrom(exception.getClass()))
            httpStatus = ((CIMException)exception).getHttpStatus();

        OperationOutcome operationOutcome = buildOperationOutcome(exception);
        String responseBody = new JsonParser().composeString(operationOutcome);

        ExchangeHelper.setInHeaderString(exchange, Exchange.HTTP_RESPONSE_CODE, httpStatus);
        ExchangeHelper.setInBodyString(exchange, responseBody);
    }

    private static OperationOutcome buildOperationOutcome(Exception exception) {

        CodeableConcept code = FhirIssueType.getCodeableConcept(FhirIssueType.TRANSIENT_EXCEPTION);

        if (CIMException.class.isAssignableFrom(exception.getClass()))
            code = ((CIMException)exception).getFhirCodeableConcept();

        OperationOutcome.OperationOutcomeIssueComponent issue = new OperationOutcome.OperationOutcomeIssueComponent()
            .setSeverity(OperationOutcome.IssueSeverity.ERROR)
            .setDetails(getExceptionMessage(exception))
            .setCode(code);

        OperationOutcome operationOutcome = new OperationOutcome();
        operationOutcome.addIssue(issue);

        return operationOutcome;
    }

    private static String getExceptionMessage(Throwable exception) {
        String result = exception.getClass().getName();

        if (!TextUtils.isNullOrTrimmedEmpty(exception.getMessage()))
            result +=  " | " + exception.getMessage();

        if (exception.getCause() != null)
            result += System.lineSeparator() + getExceptionMessage(exception.getCause());

        return result;
    }
}
