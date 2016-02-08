package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.administrative.GetOrganisationByIdProcessor;
import org.endeavourhealth.cim.processor.administrative.GetOrganisationProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationRoutes extends BaseRouteBuilder {
	public static final String GET_ORGANISATION_ROUTE = "GetOrganisation";
    public static final String GET_ORGANISATION_BY_ID_ROUTE = "GetOrganisationById";

    @Override
    public void configureRoute() throws Exception {
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_ORGANISATION_BY_ID_ROUTE)
            .process(new GetOrganisationByIdProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, GET_ORGANISATION_ROUTE)
                .process(new GetOrganisationProcessor());
    }
}
