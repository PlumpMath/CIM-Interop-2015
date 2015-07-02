package org.endeavourhealth.cim.common;

import org.hl7.fhir.instance.model.DateTimeType;
import org.hl7.fhir.instance.model.TemporalPrecisionEnum;

import java.util.ArrayList;
import java.util.Date;

/*

    Not yet implemented:

    Range/negation operators in query
    Range when date not in chronological order

*/

public class DateSearchParameter {
    public static final String OBJECT_TYPE_NOT_VALID = "object type not valid";
    public static final String DATE_TIMES_MUST_CONTAIN_TWO_ELEMENTS_ONLY = "dateTimes must contain two elements only";

    public static DateSearchParameter Parse(Object object) {
        if (object == null)
            return null;
        else if (object.getClass().equals(String.class))
            return Parse((String)object);
        else if (object.getClass().equals(ArrayList.class))
            return Parse((ArrayList<String>)object);
        else
            throw new IllegalArgumentException(OBJECT_TYPE_NOT_VALID);
    }

    public static DateSearchParameter Parse(String dateTime) {
        if (dateTime == null)
            return null;

        DateSearchParameter dateParameter = new DateSearchParameter();
        dateParameter._startInputDate = new DateTimeType(dateTime);
        return dateParameter;
    }

    public static DateSearchParameter Parse(ArrayList<String> dateTimes) {
        if (dateTimes == null)
            return null;

        if (dateTimes.size() != 2) {
            throw new IllegalArgumentException(DATE_TIMES_MUST_CONTAIN_TWO_ELEMENTS_ONLY);
        }

        DateSearchParameter dateParameter = new DateSearchParameter();
        dateParameter._startInputDate = new DateTimeType(dateTimes.get(0));
        dateParameter._endInputDate = new DateTimeType(dateTimes.get(1));
        return dateParameter;
    }

    private DateTimeType _startInputDate = null;
    private DateTimeType _endInputDate = null;

    public DateSearchParameter() {
    }

    public Date getIntervalStart() {
        return _startInputDate.getValue();
    }

    public Date getIntervalEnd() {
        if (_endInputDate == null) {
            return CalculateIntervalEnd(_startInputDate).getValue();
        } else {
            return CalculateIntervalEnd(_endInputDate).getValue();
        }
    }

    private static DateTimeType CalculateIntervalEnd(DateTimeType dateTime) {
        DateTimeType result = dateTime.copy();

        switch (dateTime.getPrecision()) {
            case YEAR:
            case MONTH:
            case DAY:
                result.add(dateTime.getPrecision().getCalendarConstant(), 1);
                result.add(TemporalPrecisionEnum.MILLI.getCalendarConstant(), -1);
                break;
            default: break;
        }

        return result;
    }
}
