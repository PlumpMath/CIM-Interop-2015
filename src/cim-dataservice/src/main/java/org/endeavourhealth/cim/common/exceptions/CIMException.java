package org.endeavourhealth.cim.common.exceptions;

import org.endeavourhealth.cim.common.FhirIssueType;
import org.endeavourhealth.cim.common.text.TextUtils;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.CodeableConcept;
import org.hl7.fhir.instance.model.OperationOutcome;

public abstract class CIMException extends Exception {

    public CIMException() {
        super();
    }
    public CIMException(String message) { super(message); }
    public CIMException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMException(Throwable cause) {
        super(cause);
    }

    public abstract int getHttpStatus();
    public abstract String getFhirIssueType();

    public CodeableConcept getFhirCodeableConcept() {
        return FhirIssueType.getCodeableConcept(getFhirIssueType());
    }

    public String getOperationOutcomeAsJson() throws Exception {
        return new JsonParser().composeString(getOperationOutcome());
    }

    public OperationOutcome getOperationOutcome() {

        CodeableConcept code = getFhirCodeableConcept();

        OperationOutcome.OperationOutcomeIssueComponent issue = new OperationOutcome.OperationOutcomeIssueComponent()
                .setSeverity(OperationOutcome.IssueSeverity.ERROR)
                .setDetails(getExceptionMessage(this))
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
