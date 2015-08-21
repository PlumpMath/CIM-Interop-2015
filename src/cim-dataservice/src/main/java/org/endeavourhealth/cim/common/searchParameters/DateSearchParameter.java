package org.endeavourhealth.cim.common.searchParameters;

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
    public static final String DATE_TIMES_MUST_CONTAIN_ONE_OR_TWO_ELEMENTS = "dateTimes must contain one or two elements";

    public static DateSearchParameter Parse(String dateTime) {
        if (dateTime == null)
            throw new IllegalArgumentException(DATE_TIMES_MUST_CONTAIN_ONE_OR_TWO_ELEMENTS);

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(dateTime);

        return Parse(arrayList);
    }

    public static DateSearchParameter Parse(ArrayList<String> dateTimes) {
        if (dateTimes == null)
            throw new IllegalArgumentException(DATE_TIMES_MUST_CONTAIN_ONE_OR_TWO_ELEMENTS);

        int size = dateTimes.size();

        if ((size < 1) || (size > 2)) {
            throw new IllegalArgumentException(DATE_TIMES_MUST_CONTAIN_ONE_OR_TWO_ELEMENTS);
        }

        DateSearchParameter dateParameter = new DateSearchParameter();
        dateParameter._startInputDate = new DateTimeType(dateTimes.get(0));

        if (size == 2)
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
