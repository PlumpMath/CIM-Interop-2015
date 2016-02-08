package org.endeavourhealth.cim.exceptions;

import org.endeavourhealth.cim.camel.helpers.FhirIssueType;
import org.endeavourhealth.core.utils.TextUtils;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.CodeableConcept;
import org.hl7.fhir.instance.model.OperationOutcome;

public abstract class BaseException extends Exception {

    public BaseException() {
        super();
    }
    public BaseException(String message) { super(message); }
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
    public BaseException(Throwable cause) {
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
                .setDiagnostics(getExceptionMessage(this))
                .setDetails(code);

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
