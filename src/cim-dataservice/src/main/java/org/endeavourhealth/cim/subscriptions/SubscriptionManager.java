package org.endeavourhealth.cim.subscriptions;

import org.apache.camel.CamelContext;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.hl7.fhir.instance.model.Subscription;

import java.util.HashMap;

public class SubscriptionManager {
	private static SubscriptionManager _instance;

	public static SubscriptionManager getInstance() {
		if (_instance == null)
			_instance = new SubscriptionManager();
		return _instance;
	}

	private HashMap<String, Subscription> _subscriptions = new HashMap<>();
	private HashMap<IDataAdapter, PollerEndpoint> _pollers = new HashMap<>();

	public void addSubscription(String odsCode, Subscription subscription, CamelContext context) throws Exception {
		if (_subscriptions.containsKey(subscription.getId()))
			return;

		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
		PollerEndpoint poller = _pollers.get(dataAdapter);
		if (poller == null) {
			poller = new PollerEndpoint(dataAdapter);
			poller.addRoutesToCamelContext(context);
			_pollers.put(dataAdapter, poller);

		}

		poller.addSubscription(subscription);
	}
}
