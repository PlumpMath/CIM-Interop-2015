package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.GetOrganisationProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationRoutes extends BaseRouteBuilder {
	public static final String GET_ORGANISATION_ROUTE = "GetOrganisation";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(GET_ORGANISATION_ROUTE)
            .process(new GetOrganisationProcessor());
    }
}
