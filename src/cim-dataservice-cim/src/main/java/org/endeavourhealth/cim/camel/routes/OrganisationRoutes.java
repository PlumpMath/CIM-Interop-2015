package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.administrative.GetOrganisationProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationRoutes extends BaseRouteBuilder {
	public static final String GET_ORGANISATION_ROUTE = "GetOrganisation";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(CimCore.ROUTE_NAME, GET_ORGANISATION_ROUTE)
            .process(new GetOrganisationProcessor());
    }
}
