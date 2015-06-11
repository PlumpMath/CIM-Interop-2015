package org.endeavourhealth.cim.subscriptions;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.processor.patient.GetChangedPatients;
import org.endeavourhealth.cim.processor.subscription.SubscriptionProcessor;
import org.hl7.fhir.instance.model.Subscription;

import java.util.ArrayList;

public class PollerEndpoint extends RouteBuilder {
    private IDataAdapter _dataAdapter;
	private GetChangedPatients _getChangedPatientsProcessor;
	private SubscriptionProcessor _subscriptionProcessor;

	public PollerEndpoint(IDataAdapter dataAdapter) {
        _dataAdapter = dataAdapter;
		_getChangedPatientsProcessor = new GetChangedPatients(_dataAdapter);
		_subscriptionProcessor = new SubscriptionProcessor();
    }

    @Override
    public void configure() throws Exception {
        from("timer://MockCMSPoller?fixedRate=true&period=10s")
            .routeId("MockCMSPoller")
            .process(_getChangedPatientsProcessor)
            .split()
            .body()
            .process(_subscriptionProcessor)
            .recipientList(header("Callbacks"));
    }

	public void addSubscription(Subscription subscription) {
		_subscriptionProcessor.add(subscription);
	}
}

