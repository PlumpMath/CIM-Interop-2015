package org.endeavourhealth.cim.camel.configuration;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.camel.spi.ManagementAgent;
import org.apache.camel.spi.ManagementStrategy;


@SuppressWarnings({"WeakerAccess", "unused"})
public class Configuration extends RouteBuilder {

    @Override
    public void configure() throws Exception {
		getContext().getManagementNameStrategy().setNamePattern("CIM-DataService-#name#");
		Boolean trace = Boolean.parseBoolean(getContext().getProperty("enableCamelTrace"));
		Tracer tracer = new Tracer();
		tracer.getDefaultTraceFormatter().setShowBreadCrumb(false);
		tracer.getDefaultTraceFormatter().setShowBody(false);
		getContext().addInterceptStrategy(tracer);
        getContext().setTracing(trace);

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
