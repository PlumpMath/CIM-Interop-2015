package org.endeavourhealth.common.core;

import org.hl7.fhir.instance.model.CodeableConcept;
import org.hl7.fhir.instance.model.Coding;

import java.util.HashMap;

public class FhirIssueType {

    public static final String CODE_SYSTEM = "http://hl7.org/fhir/issue-type";

    public static final String INVALID = "invalid";
    public static final String INVALID_STRUCTURE = "structure";
    public static final String INVALID_REQUIRED = "required";
    public static final String INVALID_VALUE = "value";
    public static final String INVALID_INVARIANT = "invariant";
    public static final String SECURITY = "security";
    public static final String SECURITY_LOGIN = "login";
    public static final String SECURITY_UNKNOWN = "unknown";
    public static final String SECURITY_EXPIRED = "expired";
    public static final String SECURITY_FORBIDDEN = "forbidden";
    public static final String SECURITY_SUPRESSED = "supressed";
    public static final String PROCESSING = "processing";
    public static final String PROCESSING_NOTSUPPORTED = "not-supported";
    public static final String PROCESSING_DUPLICATE = "duplicate";
    public static final String PROCESSING_NOTFOUND = "not-found";
    public static final String PROCESSING_TOOLONG = "too-long";
    public static final String PROCESSING_CODEINVALID = "code-invalid";
    public static final String PROCESSING_EXTENSION = "extension";
    public static final String PROCESSING_TOOCOSTLY = "too-costly";
    public static final String PROCESSING_BUSINESSRULES = "business-rule";
    public static final String PROCESSING_CONFLICT = "conflict";
    public static final String PROCESSING_INCOMPLETE = "incomplete";
    public static final String TRANSIENT = "transient";
    public static final String TRANSIENT_LOCKERROR = "lock-error";
    public static final String TRANSIENT_NOSTORE = "no-store";
    public static final String TRANSIENT_EXCEPTION = "exception";
    public static final String TRANSIENT_TIMEOUT = "timeout";
    public static final String TRANSIENT_THROTTLED = "throttled";
    public static final String INFORMATIONAL = "informational";

    public static String getDescription(String issueType) {
        if (descriptionMap.containsKey(issueType))
            return descriptionMap.get(issueType);

        return null;
    }

    public static CodeableConcept getCodeableConcept(String issueType) {
        return new CodeableConcept()
            .addCoding(new Coding()
                    .setSystem(FhirIssueType.CODE_SYSTEM)
                    .setCode(issueType)
                    .setDisplay(getDescription(issueType)));
    }

    private static final HashMap<String, String> descriptionMap = new HashMap<>();

    static {
        descriptionMap.put(INVALID, "Invalid Content");
        descriptionMap.put(INVALID_STRUCTURE, "Structural Issue");
        descriptionMap.put(INVALID_REQUIRED, "Required element missing");
        descriptionMap.put(INVALID_VALUE, "Element value invalid");
        descriptionMap.put(INVALID_INVARIANT, "Validation rule failed");
        descriptionMap.put(SECURITY, "Security Problem");
        descriptionMap.put(SECURITY_LOGIN, "Login Required");
        descriptionMap.put(SECURITY_UNKNOWN, "Unknown User");
        descriptionMap.put(SECURITY_EXPIRED, "Session Expired");
        descriptionMap.put(SECURITY_FORBIDDEN, "Forbidden");
        descriptionMap.put(SECURITY_SUPRESSED, "Information Suppressed");
        descriptionMap.put(PROCESSING, "Processing Failure");
        descriptionMap.put(PROCESSING_NOTSUPPORTED, "Content not supported");
        descriptionMap.put(PROCESSING_DUPLICATE, "Duplicate");
        descriptionMap.put(PROCESSING_NOTFOUND, "Not Found");
        descriptionMap.put(PROCESSING_TOOLONG, "Content Too Long");
        descriptionMap.put(PROCESSING_CODEINVALID, "Invalid Code");
        descriptionMap.put(PROCESSING_EXTENSION, "Unacceptable Extension");
        descriptionMap.put(PROCESSING_TOOCOSTLY, "Operation Too Costly");
        descriptionMap.put(PROCESSING_BUSINESSRULES, "Business Rule Violation");
        descriptionMap.put(PROCESSING_CONFLICT, "Edit Version Conflict");
        descriptionMap.put(PROCESSING_INCOMPLETE, "Incomplete Results");
        descriptionMap.put(TRANSIENT, "Transient Issue");
        descriptionMap.put(TRANSIENT_LOCKERROR, "Lock Error");
        descriptionMap.put(TRANSIENT_NOSTORE, "No Store Available");
        descriptionMap.put(TRANSIENT_EXCEPTION, "Exception");
        descriptionMap.put(TRANSIENT_TIMEOUT, "Timeout");
        descriptionMap.put(TRANSIENT_THROTTLED, "Throttled");
        descriptionMap.put(INFORMATIONAL, "Informational Note");
    }
}