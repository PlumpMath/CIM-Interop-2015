package org.endeavourhealth.async.camel.routes;

import org.endeavourhealth.async.processor.TransformationToFhir;
import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.common.camel.routes.FhirValidation;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;
import org.endeavourhealth.common.processor.AuditProcessor;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TransformToFhir extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "TransformToFhir";
	public static final String CONTENT_TYPE = "ContentType";

    @Override
    public void configureRoute() throws Exception {

        from(direct(ROUTE_NAME))
            .routeId(ROUTE_NAME)
				.choice()
					.when(simple("${header."+ CONTENT_TYPE+"} == null || ${header."+CONTENT_TYPE+"} == \"\""))
						.throwException(new SourceDocumentInvalidException("Content type not specified"))
					.otherwise()
						.process(new TransformationToFhir())
						// .to(direct(ComponentRouteName.FHIR_VALIDATION)) Transformer currently returns JSON not XML
				.end();
    }
}
