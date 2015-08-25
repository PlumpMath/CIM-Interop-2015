package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.math.BigDecimal;
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

    public static Range convertAgeRange(DtAgeRange source) throws TransformFeatureNotSupportedException {
        if (source == null)
            return null;

        Range target = new Range();

        //TODO: Age Unit - Consider coding the unit

        if (source.getLow() != null) {
            target.setLow(new Quantity()
                    .setValue(new BigDecimal(source.getLow().getValue()))
                    .setUnits(convertAgeUnit(source.getLow().getUnit())));
        }

        if (source.getHigh() != null) {
            target.setHigh(new Quantity()
                    .setValue(new BigDecimal(source.getHigh().getValue()))
                    .setUnits(convertAgeUnit(source.getHigh().getUnit())));
        }

        return target;
    }

    private static String convertAgeUnit(VocAgeUnit ageUnit) throws TransformFeatureNotSupportedException {
        switch (ageUnit) {
            case D:
                return "day";
            case W:
                return "week";
            case M:
                return "month";
            case Y:
                return "year";
            default:
                throw new TransformFeatureNotSupportedException("Age Unit not supported: " + ageUnit.toString());
        }
    }

}
