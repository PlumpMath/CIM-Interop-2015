package org.endeavourhealth.cim.subscriptions;

import org.apache.camel.CamelContext;
import org.endeavourhealth.cim.Registry;
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
	private HashMap<String, PollerEndpoint> _pollers = new HashMap<>();

	public void addSubscription(String odsCode, Subscription subscription, CamelContext context) throws Exception {
		if (_subscriptions.containsKey(subscription.getId()))
			return;

		String adapterType = Registry.getDataAdapterTypeNameForService(odsCode);
		PollerEndpoint poller = _pollers.get(adapterType);
		if (poller == null) {
			IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
			poller = new PollerEndpoint(dataAdapter);
			poller.addRoutesToCamelContext(context);
			_pollers.put(adapterType, poller);

		}

		poller.addSubscription(odsCode, subscription);
	}
}
