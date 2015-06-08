package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.DtDatePart;
import org.endeavourhealth.cim.transform.schemas.openhr.DtDbo;
import org.endeavourhealth.cim.transform.schemas.openhr.VocDatePart;
import org.endeavourhealth.cim.transform.schemas.openhr.VocUpdateMode;
import org.hl7.fhir.instance.model.DateTimeType;
import org.hl7.fhir.instance.model.DateType;
import org.hl7.fhir.instance.model.Period;
import org.hl7.fhir.instance.model.TemporalPrecisionEnum;

import java.util.UUID;

public class ToFHIRHelper {
    public static UUID parseUUID(String id) throws SourceDocumentInvalidException {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new SourceDocumentInvalidException("Could not parse UUID: " + id, e);
        }
    }

    public static void ensureDboNotDelete(DtDbo dbo) throws TransformFeatureNotSupportedException {
        ensureDboNotDelete(dbo.getUpdateMode());
    }

    public static void ensureDboNotDelete(VocUpdateMode updateMode) throws TransformFeatureNotSupportedException {
        if (updateMode == VocUpdateMode.DELETE)
            throw new TransformFeatureNotSupportedException("DBO type of Delete not supported");
    }

    public static DateTimeType convertPartialDateTimeToDateTimeType(DtDatePart source) throws TransformFeatureNotSupportedException {
        if (source == null)
            return null;

        if (source.getDatepart() == VocDatePart.U)
            return null;

        switch (source.getDatepart()) {
            case Y:
                return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.YEAR);
            case YM:
                return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.MONTH);
            case YMD:
                return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.DAY);
            case YMDT:
                return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.SECOND);
            default:
                throw new TransformFeatureNotSupportedException("Date part not supported: " + source.getDatepart().toString());
        }
    }

    public static DateType convertPartialDateTimeToDateType(DtDatePart source) throws TransformFeatureNotSupportedException {
        if (source == null)
            return null;

        if (source.getDatepart() == VocDatePart.U)
            return null;

        switch (source.getDatepart()) {
            case Y:
                return new DateType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.YEAR);
            case YM:
                return new DateType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.MONTH);
            case YMD:
            case YMDT:
                return new DateType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.DAY);
            default:
                throw new TransformFeatureNotSupportedException("Date part not supported: " + source.getDatepart().toString());
        }
    }

}
