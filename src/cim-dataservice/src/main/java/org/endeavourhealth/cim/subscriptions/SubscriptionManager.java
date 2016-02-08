package org.endeavourhealth.cim.subscriptions;

import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.CamelContext;
import org.endeavourhealth.common.IRegistry;
import org.endeavourhealth.common.Registry;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Subscription;

import java.util.HashMap;

public class SubscriptionManager {
	private static SubscriptionManager _instance;

	public static SubscriptionManager getInstance() {
		if (_instance == null)
			_instance = new SubscriptionManager();
		return _instance;
	}

	private final HashMap<String, Subscription> _subscriptions = new HashMap<>();
	private final HashMap<String, PollerEndpoint> _pollers = new HashMap<>();
	private final IRegistry _registry = new Registry();

	public void addSubscription(String odsCode, String subscriptionJson, CamelContext context) throws Exception {
		Subscription subscription = (Subscription)new JsonParser().parse(subscriptionJson);
		addSubscription(odsCode, subscription, context);
	}

	public void addSubscription(String odsCode, Subscription subscription, CamelContext context) throws Exception {
		if (_subscriptions.containsKey(subscription.getId()))
			return;

		_subscriptions.put(subscription.getId(), subscription);

		String dataManagerType = _registry.getDataManagerTypeNameForService(odsCode);
		PollerEndpoint poller = _pollers.get(dataManagerType);
		if (poller == null) {
			IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
			poller = new PollerEndpoint(dataManager);
			poller.addRoutesToCamelContext(context);
			_pollers.put(dataManagerType, poller);

		}

		poller.addSubscription(odsCode, subscription);
	}
}
