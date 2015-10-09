package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.GetOrganisationProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationRoutes extends CIMRouteBuilder {
	public static final String GET_ORGANISATION_ROUTE = "GetOrganisation";

    @Override
    public void configureRoute() throws Exception {
        buildCimCallbackRoute(GET_ORGANISATION_ROUTE)
            .process(new GetOrganisationProcessor());
    }
}
