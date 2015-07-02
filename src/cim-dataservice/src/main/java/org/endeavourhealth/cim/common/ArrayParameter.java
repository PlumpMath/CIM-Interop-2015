package org.endeavourhealth.cim.common;

import java.util.ArrayList;

public class ArrayParameter {
    private ArrayParameter() {
    }

    public static ArrayList Receive(Object object) {
        if (object == null)
            return null;

        if (object.getClass().equals(ArrayList.class))
            return (ArrayList)object;

        ArrayList arrayList = new ArrayList();
        arrayList.add(object);

        return arrayList;
    }
}
