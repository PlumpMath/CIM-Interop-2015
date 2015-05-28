package org.endeavourhealth.cim.routes.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RestConfiguration extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // enable debug output
        getContext().setTracing(true);

        restConfiguration().component("servlet")
                .bindingMode(RestBindingMode.auto)
                .dataFormatProperty("prettyPrint", "true");
    }
}
