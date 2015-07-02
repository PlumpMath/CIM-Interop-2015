package org.endeavourhealth.cim.common;

import java.util.ArrayList;

public class ArrayListHelper {
    private ArrayListHelper() {
    }

    public static ArrayList FromSingleOrArray(Object object) {
        if (object == null)
            return null;

        if (object.getClass().equals(ArrayList.class))
            return (ArrayList)object;

        ArrayList arrayList = new ArrayList();
        arrayList.add(object);

        return arrayList;
    }
}
