package org.endeavourhealth.cim.routes.pollers;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.patient.GetChangedPatients;
import org.endeavourhealth.cim.processor.subscription.AddSubscription;
import org.endeavourhealth.cim.processor.subscription.PollerProcessor;

import java.util.Date;

public class MockCMSPoller extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer://MockCMSPoller?fixedRate=true&period=10s")
            .routeId("MockCMSPoller")
            .process(new GetChangedPatients())
            .split()
            .body()
            .process(PollerProcessor.getInstance())
            .recipientList(header("Callbacks"));
    }
}

