package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.FhirConstants;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.openhr.DtCodeQualified;
import org.hl7.fhir.instance.model.CodeableConcept;
import org.hl7.fhir.instance.model.Coding;

class CodeHelper {
    public static CodeableConcept convertCode(DtCodeQualified sourceCode) throws TransformFeatureNotSupportedException
    {
        return convertCode(sourceCode, null);
    }

    public static CodeableConcept convertCode(DtCodeQualified sourceCode, String sourceTerm) throws TransformFeatureNotSupportedException {
        CodeableConcept result = new CodeableConcept();

        if (StringUtils.isNotBlank(sourceTerm))
            result.setText(sourceTerm);

        if (sourceCode != null)
            addCode(result, sourceCode);

        return result;
    }

    private static void addCode(CodeableConcept codeableConcept, DtCodeQualified sourceCode) throws TransformFeatureNotSupportedException {
        codeableConcept.addCoding(new Coding()
                .setCode(sourceCode.getCode())
                .setDisplay(sourceCode.getDisplayName())
                .setSystem(convertCodeSystem(sourceCode.getCodeSystem())));

        if (sourceCode.getTranslation() != null) {
            for (DtCodeQualified code : sourceCode.getTranslation())
                addCode(codeableConcept, code);
        }
    }

    private static String convertCodeSystem(String sourceCodeSystem) throws TransformFeatureNotSupportedException {
        switch (sourceCodeSystem) {
            case "2.16.840.1.113883.2.1.6.2":
                return FhirConstants.CODE_SYSTEM_READ2;
            case "2.16.840.1.113883.2.1.3.2.4.15":
                return FhirConstants.CODE_SYSTEM_SNOMED_CT;
            case "2.16.840.1.113883.2.1.6.3":
                return "http://www.e-mis.com/emisopen/emis_snomed";
            case "EMISPREPARATION":
                return "http://www.e-mis.com/emisopen/emis_preparation";
            default:
                throw new TransformFeatureNotSupportedException("CodeSystem not supported: " + sourceCodeSystem);
        }
    }

    public static boolean hasQualifier(DtCodeQualified code, String name, String value) {
        if (code == null)
            return false;

        return code.getQualifier()
                .stream()
                .anyMatch(q -> q.getName().getDisplayName().equals(name)
                        && q.getValue().getDisplayName().equals(value));
    }

    public static boolean isBloodPressureCode(DtCodeQualified code)
    {
        if (code == null)
            return false;

        if (code.getCodeSystem().equals("2.16.840.1.113883.2.1.6.2") && code.getCode().equals("246"))
            return true;

        if (code.getTranslation() != null)
        {
            if (code.getTranslation()
                    .stream()
                    .anyMatch(t -> t.getCodeSystem().equals("2.16.840.1.113883.2.1.6.2")
                            && t.getCode().equals("246"))) {
                return true;
            }
        }

        return false;
    }
}
