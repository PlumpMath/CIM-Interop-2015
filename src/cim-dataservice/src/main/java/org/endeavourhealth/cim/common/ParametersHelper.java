package org.endeavourhealth.cim.common;

import org.hl7.fhir.instance.model.Parameters;
import org.hl7.fhir.instance.model.StringType;

public class ParametersHelper {

    public static String getStringParameter(Parameters parameters, String name) {

        for (Parameters.ParametersParameterComponent parameter : parameters.getParameter())
            if (parameter.getName().equals(name))
                return ((StringType)parameter.getValue()).getValue();

        return null;
    }
}