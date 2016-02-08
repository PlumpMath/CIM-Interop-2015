package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings("unused")
public class FhirValidation extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.FHIR_VALIDATION))
            .routeId(ComponentRouteName.FHIR_VALIDATION)
			.to("validator:fhir-single.xsd");
    }
}
