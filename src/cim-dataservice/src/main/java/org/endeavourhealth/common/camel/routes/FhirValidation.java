package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class FhirValidation extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.FHIR_VALIDATION))
            .routeId(ComponentRouteName.FHIR_VALIDATION)
			.to("validator:fhir-single.xsd");
    }
}
