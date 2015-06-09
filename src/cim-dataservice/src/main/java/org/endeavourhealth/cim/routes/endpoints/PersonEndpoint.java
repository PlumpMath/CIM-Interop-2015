package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.common.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.processor.patient.TracePersonProcessor;
import org.endeavourhealth.cim.processor.patient.TracePersonResultProcessor;

public class PersonEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/Person")
            .description("Person rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get("/$trace?nhsNo={nhsNo}&surname={surname}&dob={dob}&gender={gender}")
            .route()
            .routeId("GetTracePerson")
            .description("Person trace")
            .setHeader("MessageRouterCallback", constant("direct:TracePerson"))
            .to("direct:CIMCore")
            .endRest();

        // Message router callback routes
        from("direct:TracePerson")
            .routeId("TracePerson")
            .setBody(constant(AdapterFactory.getAllDataAdapters()))     // Get addapters as array list in body
            .split(body())                                              // Split the body and call trace for each element...
                // .parallelProcessing()                                // ...in parallel...
                .process(new TracePersonProcessor())
            .aggregate(header("id"), new ArrayListAggregationStrategy())// Aggregate the results into an array list in the body
            .completionTimeout(3000)                                    // Timeout after 3 seconds if no response
            .process(new TracePersonResultProcessor());                 // Process the results
    }
}
