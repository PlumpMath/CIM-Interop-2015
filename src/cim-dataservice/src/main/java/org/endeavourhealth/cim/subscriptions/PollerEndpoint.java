package org.endeavourhealth.cim.subscriptions;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.processor.patient.GetChangedPatients;
import org.endeavourhealth.cim.processor.subscription.EvaluateSubscriptionsProcessor;
import org.hl7.fhir.instance.model.Subscription;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class PollerEndpoint extends RouteBuilder {
    private final IDataManager _dataManager;
	private final EvaluateSubscriptionsProcessor _subscriptionProcessor;
	private final ArrayList<String> _odsCodes = new ArrayList<>();

	public PollerEndpoint(IDataManager dataManager) {
        _dataManager = dataManager;
		_subscriptionProcessor = new EvaluateSubscriptionsProcessor();
    }

    @Override
    public void configure() throws Exception {
        from("timer://MockCMSPoller?fixedRate=true&period=10s")
            .routeId("MockCMSPoller")
			.setHeader(HeaderKey.OdsCodeList, constant(_odsCodes))
			.split(header(HeaderKey.OdsCodeList), new ArrayListAggregationStrategy())	// Split the call by single ods code
				.setHeader(HeaderKey.OdsCode, simple("${header." + HeaderKey.OdsCodeList +"[0]}"))		// Set the odsCode
            	.process(new GetChangedPatients(_dataManager))				// Call the adapter for each ods code (returns array)
			.end()															// Aggregate the results (array of arrays)
			.choice()
				.when(simple("${body.size} > 0"))							// When there are changed patients
					.split(body())											// Split array...
						.split(body())										// ...of arrays
							.process(_subscriptionProcessor)                // Process subscriptions for individual patient
							.recipientList(header(HeaderKey.Callbacks))		// Publish single patient change to multiple subscribers
						.end()
					.end()
				.end()
			.end();
    }

	public void addSubscription(String odsCode, Subscription subscription) {
		if (!_odsCodes.contains(odsCode))
			_odsCodes.add(odsCode);

		_subscriptionProcessor.add(subscription);
	}
}

