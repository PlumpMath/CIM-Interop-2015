package org.endeavourhealth.cim.common;

public class TextUtils {

    public static boolean isNullOrEmpty(String param) {
        return param == null || param.length() == 0;
    }

    public static boolean isNullOrTrimmedEmpty(String param) {
        return param == null || param.trim().length() == 0;
    }
}
