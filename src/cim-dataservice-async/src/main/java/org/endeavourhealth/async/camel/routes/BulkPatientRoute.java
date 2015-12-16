package org.endeavourhealth.async.camel.routes;

import org.endeavourhealth.async.processor.*;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.PropertyKey;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BulkPatientRoute extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "BulkPatient";

	@Override
	public void configureRoute() throws Exception {
		buildWrappedRoute(RouteWrapper.ROUTE_NAME, ROUTE_NAME)
			.process(new CacheFullRecord())
			.setProperty("BODY", body())
			.split(exchangeProperty(PropertyKey.InformationSharingProtocols))
				.setProperty(PropertyKey.InformationSharingProtocols, body())
				.setBody(exchangeProperty("BODY"))
				.to(direct(ComponentRouteName.DATA_PROTOCOL_FILTER))
				.process(new GetProtocolSubscribers())
				.recipientList(exchangeProperty(GetProtocolSubscribers.SUBSCRIBER_LIST_PROPERTY))
			.end();
	}
}
