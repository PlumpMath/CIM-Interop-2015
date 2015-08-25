package org.endeavourhealth.cim.routes.routeBuilders.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.camel.spi.ManagementAgent;
import org.apache.camel.spi.ManagementStrategy;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;


@SuppressWarnings({"WeakerAccess", "unused"})
public class Configuration extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
            .to(Route.direct(CoreRouteName.CIM_CRITICAL_ERROR))
            .stop();

		getContext().getManagementNameStrategy().setNamePattern("CIM-DataService-#name#");

		Tracer tracer = new Tracer();
		tracer.getDefaultTraceFormatter().setShowBreadCrumb(false);
		getContext().addInterceptStrategy(tracer);
        getContext().setTracing(true);

        getContext().setAllowUseOriginalMessage(false);

		ManagementStrategy strat = getContext().getManagementStrategy();
		ManagementAgent agt = strat.getManagementAgent();

		if (agt != null) {
			agt.setRegistryPort(8888);
			agt.setCreateConnector(true);
		}

        restConfiguration().component("servlet")
            .bindingMode(RestBindingMode.off)
            .dataFormatProperty("prettyPrint", "true");
    }
}
