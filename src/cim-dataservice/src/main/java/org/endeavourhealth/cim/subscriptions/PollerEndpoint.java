package org.endeavourhealth.cim.subscriptions;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.common.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.processor.patient.GetChangedPatients;
import org.endeavourhealth.cim.processor.subscription.SubscriptionProcessor;
import org.hl7.fhir.instance.model.Subscription;

import java.util.ArrayList;

public class PollerEndpoint extends RouteBuilder {
    private IDataAdapter _dataAdapter;
	private SubscriptionProcessor _subscriptionProcessor;
	private ArrayList<String> _odsCodes = new ArrayList<>();

	public PollerEndpoint(IDataAdapter dataAdapter) {
        _dataAdapter = dataAdapter;
		_subscriptionProcessor = new SubscriptionProcessor();
    }

    @Override
    public void configure() throws Exception {
        from("timer://MockCMSPoller?fixedRate=true&period=10s")
            .routeId("MockCMSPoller")
			.setHeader("odsCodes", constant(_odsCodes))
			.split(header("odsCodes"), new ArrayListAggregationStrategy())	// Split the call by single ods code
				.setHeader("odsCode", simple("${header.odsCodes[0]}"))		// Set the odsCode
            	.process(new GetChangedPatients(_dataAdapter))				// Call the adapter for each ods code (returns array)
			.end()															// Aggregate the results (array of arrays)
			.choice()
				.when(simple("${body.size} > 0"))							// When there are changed patients
					.split(body())											// Split array...
						.split(body())										// ...of arrays
							.process(_subscriptionProcessor)                // Process subscriptions for individual patient
							.recipientList(header("Callbacks"))				// Publish single patient change to multiple subscribers
						.end()
					.end()
				.end()
			.end();
    }

	public void addSubscription(String odsCode, Subscription subscription) {
		if (_odsCodes.contains(odsCode) == false)
			_odsCodes.add(odsCode);

		_subscriptionProcessor.add(subscription);
	}
}

