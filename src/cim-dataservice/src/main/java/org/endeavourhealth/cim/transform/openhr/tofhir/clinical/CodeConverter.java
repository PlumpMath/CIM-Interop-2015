package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.FHIRConstants;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.openhr.DtCodeQualified;
import org.hl7.fhir.instance.model.CodeableConcept;
import org.hl7.fhir.instance.model.Coding;

class CodeConverter {
    public static CodeableConcept convertCode(DtCodeQualified sourceCode) throws TransformFeatureNotSupportedException {
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
                return "READ2";
            case "2.16.840.1.113883.2.1.3.2.4.15":
                return FHIRConstants.CODE_SYSTEM_SNOMED_CT;
            case "2.16.840.1.113883.2.1.6.3":
                return "http://www.e-mis.com/emisopen/emis_snomed";
            default:
                throw new TransformFeatureNotSupportedException("CodeSystem not supported: " + sourceCodeSystem);
        }
    }
}
