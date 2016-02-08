package org.endeavourhealth.cim.camel.helpers;

import org.hl7.fhir.instance.model.Parameters;
import org.hl7.fhir.instance.model.StringType;

import java.util.UUID;

public class ParametersHelper {

    public static String getStringParameter(Parameters parameters, String name) {

        for (Parameters.ParametersParameterComponent parameter : parameters.getParameter())
            if (parameter.getName().equals(name))
                return ((StringType)parameter.getValue()).getValue();

        return null;
    }

    public static UUID getUUIDParameter(Parameters parameters, String name) {

        return UUID.fromString(getStringParameter(parameters, name));
    }
}