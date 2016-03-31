package org.endeavourhealth.cim.transform.common;

public class FhirUris
{
    public final static String IDENTIFIER_SYSTEM_NHSNUMBER = "http://fhir.nhs.net/Id/nhs-number";
    public final static String IDENTIFIER_SYSTEM_ODS_CODE = "http://fhir.nhs.net/Id/ods-organization-code";
    public final static String IDENTIFIER_SYSTEM_EMIS_CDB = "http://www.endeavourhealth.org/fhir/Id/emis-cdb";

    public final static String CODE_SYSTEM_READ2 = "http://endeavourhealth.org/fhir/read2";
    public final static String CODE_SYSTEM_SNOMED_CT = "http://snomed.info/sct";

    public final static String PROFILE_URI_ORGANIZATION = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-organization";

    public final static String EXTENSION_URI_ACTIVEPERIOD = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-activeperiod-extension";
    public final static String EXTENSION_URI_MAINLOCATION = "http://endeavourhealth.org/fhir/StructureDefinition/primarycare-mainlocation-extension";
    public final static String EXTENSION_URL_SCHEDULEADDITIONALACTOR = "http://www.endeavourhealth.org/fhir/extension/Schedule/AdditionalActor";



    public final static String LOCATIONTYPE_EXTENSION_URL = "http://www.e-mis.com/emisopen/extension/LocationType";
    public final static String LOCATIONTYPE_SYSTEM = "http://www.e-mis.com/emisopen/LocationType";
    public final static String TASKTYPE_EXTENSION_URL = "http://cim-api.endeavourhealth.org/fhir/ValueSet/task-type";
    public final static String TASKTYPE_SYSTEM = "http://cim-api.endeavourhealth.org/fhir/task-type";
    public final static String TASKPRIORITY_EXTENSION_URL = "http://cim-api.endeavourhealth.org/fhir/ValueSet/task-priority";
    public final static String TASKPRIORITY_SYSTEM = "http://cim-api.endeavourhealth.org/fhir/task-priority";
    public final static String TASKSTATUS_EXTENSION_URL = "http://cim-api.endeavourhealth.org/fhir/ValueSet/task-status";
    public final static String TASKSTATUS_SYSTEM = "http://cim-api.endeavourhealth.org/fhir/task-status";


}
