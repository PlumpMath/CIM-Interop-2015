package org.endeavourhealth.cim.common;

import org.endeavourhealth.core.utils.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NhsNumberValidator {

    public static Boolean IsValidNhsNumber(String nhsNumber)
    {
        if (TextUtils.isNullOrTrimmedEmpty(nhsNumber))
            return false;

		nhsNumber = nhsNumber.replace(" ","");

        Pattern tenDigitsPattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher = tenDigitsPattern.matcher(nhsNumber);

        if (!matcher.matches())
            return false;

        int sum = 0;
        int factor = 10;

        if (nhsNumber.length() != 10)
            return false;

        for (int i = 0; i < (nhsNumber.length() - 1); i++)
        {
            sum += (Integer.parseInt(Character.toString(nhsNumber.charAt(i)))) * factor;
            factor--;
        }

        int remainder = sum % 11;
        int checkDigit = 11 - remainder;

        if (checkDigit == 11)
            checkDigit = 0;

        if (checkDigit == 10)
            return false;

        return (Integer.parseInt(Character.toString(nhsNumber.charAt(9))) == checkDigit);
    }
}
