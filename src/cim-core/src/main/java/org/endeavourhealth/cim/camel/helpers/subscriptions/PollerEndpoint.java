package org.endeavourhealth.cim.camel.helpers.subscriptions;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.camel.helpers.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.camel.processors.demographics.GetChangedPatientsProcessor;
import org.endeavourhealth.cim.camel.processors.demographics.EvaluateSubscriptionsProcessor;
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
			.setHeader(CIMHeaderKey.OdsCodeList, constant(_odsCodes))
			.split(header(CIMHeaderKey.OdsCodeList), new ArrayListAggregationStrategy())	// Split the call by single ods code
				.setHeader(CIMHeaderKey.DestinationOdsCode, simple("${header." + CIMHeaderKey.OdsCodeList +"[0]}"))		// Set the odsCode
            	.process(new GetChangedPatientsProcessor(_dataManager))				// Call the adapter for each ods code (returns array)
			.end()															// Aggregate the results (array of arrays)
			.choice()
				.when(simple("${body.size} > 0"))							// When there are changed patients
					.split(body())											// Split array...
						.split(body())										// ...of arrays
							.process(_subscriptionProcessor)                // Process subscriptions for individual patient
							.recipientList(header(CIMHeaderKey.Callbacks))		// Publish single patient change to multiple subscribers
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

