package org.endeavourhealth.common.camel;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.core.repository.rabbit.RabbitConfig;
import org.endeavourhealth.core.repository.rabbit.ConfigRepository;
import org.endeavourhealth.core.serializer.DeserializationException;

public class QueueReader extends BaseRouteBuilder {
	public static final String DESTINATION_ROUTE = "DestinationRoute";
	@Override
	public void configureRoute() throws Exception {
		String readerQueueName = getContext().getProperty("readerQueueName");
		if (readerQueueName == null || readerQueueName.isEmpty())
			return;

		from(rabbitQueueReader(readerQueueName))
			.routeId("QueueReader")
			.removeHeaders("rabbitmq.*")	// Needed to prevent overriding routingKey in camel endpoint
			.convertBodyTo(String.class)
			.recipientList(simple("${header."+DESTINATION_ROUTE+"}"));
	}

	private String rabbitQueueReader(String readerQueueName) throws RepositoryException, DeserializationException {
		RabbitConfig rabbitConfig = ConfigRepository.getInstance().getConfigByName("rabbit", RabbitConfig.class);
		final String RMQ_EXCHANGE = rabbitConfig.getUri() + "/" + rabbitConfig.getExchange();
		final String RMQ_OPTIONS = "?autoAck=false&autoDelete=false&automaticRecoveryEnabled=true&durable=true&"+rabbitConfig.getUsernamePassword();
		final String RMQ_ROUTING = "&queue=" + readerQueueName + "&routingKey=" + readerQueueName;

		return "rabbitmq://" + RMQ_EXCHANGE + RMQ_OPTIONS + RMQ_ROUTING;
	}

}
