package org.endeavourhealth.cim.routes.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.interceptor.Tracer;

@SuppressWarnings("WeakerAccess")
public class RestConfiguration extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // enable debug output
        getContext().setTracing(true);

        Tracer tracer = new Tracer();
        tracer.getDefaultTraceFormatter().setShowBreadCrumb(false);
        tracer.getDefaultTraceFormatter().setShowExchangePattern(false);
        tracer.getDefaultTraceFormatter().setShowHeaders(false);
        tracer.getDefaultTraceFormatter().setShowBody(false);
        tracer.getDefaultTraceFormatter().setShowBodyType(false);
        tracer.getDefaultTraceFormatter().setShowNode(false);


        // getContext().addInterceptStrategy(tracer);

        restConfiguration().component("servlet")
                .bindingMode(RestBindingMode.off)
                .dataFormatProperty("prettyPrint", "true");
    }
}
