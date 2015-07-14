package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.processor.patient.TracePersonProcessor;
import org.endeavourhealth.cim.processor.patient.TracePersonResultProcessor;

@SuppressWarnings("WeakerAccess")
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
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:TracePerson"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:TracePerson")
            .routeId("TracePerson")
            .setBody(constant(DataManagerFactory.getAllDataAdapters()))         // Get adapters as array list in body
            .setHeader(HeaderKey.AdapterCount, simple("${body.size}"))
            .split(body(), new ArrayListAggregationStrategy())              // Split the body and call trace for each element...
                .parallelProcessing()                                       // ...in parallel...
                .process(new TracePersonProcessor())
            .end()
            .process(new TracePersonResultProcessor());                     // Process the results
    }
}
