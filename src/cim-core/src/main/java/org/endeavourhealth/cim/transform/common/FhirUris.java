package org.endeavourhealth.cim.transform.common;

public class FhirUris
{
    public final static String IDENTIFIER_SYSTEM_NHSNUMBER = "http://fhir.nhs.net/Id/nhs-number";
    public final static String IDENTIFIER_SYSTEM_CHINUMBER = "http://www.endeavourhealth.org/fhir/Identifier/chinumber";
    public final static String IDENTIFIER_SYSTEM_ODS_CODE = "http://fhir.nhs.net/Id/ods-organization-code";
    public final static String IDENTIFIER_SYSTEM_GMC_NUMBER = "http://endeavourhealth.org/fhir/Identifier/gmc-number";
    public final static String IDENTIFIER_SYSTEM_DOCTOR_INDEX_NUMBER = "http://endeavourhealth.org/fhir/Identifier/doctor-index-number";
    public final static String IDENTIFIER_SYSTEM_GMP_PPD_CODE = "http://endeavourhealth.org/fhir/Identifier/gmp-ppd-code";

    public final static String CODE_SYSTEM_READ2 = "http://endeavourhealth.org/fhir/read2";
    public final static String CODE_SYSTEM_SNOMED_CT = "http://snomed.info/sct";

    public final static String PROFILE_URI_ORGANIZATION = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-organization";
    public final static String PROFILE_URI_LOCATION = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-location";
    public final static String PROFILE_URI_PRACTITIONER = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-practitioner";
    public final static String PROFILE_URI_SCHEDULE = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-schedule";
    public final static String PROFILE_URI_SLOT = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-slot";
    public final static String PROFILE_URI_APPOINTMENT = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-appointment";

    public final static String EXTENSION_URI_ACTIVEPERIOD = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-activeperiod-extension";
    public final static String EXTENSION_URI_MAINLOCATION = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-mainlocation-extension";
    public final static String EXTENSION_URI_LOCATIONEXTENSION = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-location-extension";
    public final static String EXTENSION_URI_ADDITIONALACTOREXTENSION = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-additional-actor-extension";
    public final static String EXTENSION_URI_TASKTYPE = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-task-type-extension";
    public final static String EXTENSION_URI_TASKSTATUS = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-task-status-extension";
    public final static String EXTENSION_URI_TASKPRIORITY = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-task-priority-extension";
}
