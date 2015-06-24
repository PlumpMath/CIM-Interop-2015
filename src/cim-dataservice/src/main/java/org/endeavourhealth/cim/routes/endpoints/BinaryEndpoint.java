package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.common.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.processor.binary.RetrieveBinaryObjectProcessor;
import org.endeavourhealth.cim.processor.patient.TracePersonProcessor;
import org.endeavourhealth.cim.processor.patient.TracePersonResultProcessor;

public class BinaryEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/Binary")
            .description("Binary object (e.g. documents) rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get("/{id}")
            .route()
            .routeId("GetBinary")
            .description("Retrieve binary object")
            .setHeader("MessageRouterCallback", constant("direct:RetrieveBinaryObject"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:RetrieveBinaryObject")
            .routeId("RetrieveBinaryObject")
            .process(new RetrieveBinaryObjectProcessor());                     // Process the results
    }
}
