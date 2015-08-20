package org.endeavourhealth.cim.processor.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.http.entity.ContentType;

public class ResponseProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
		exchange.getOut().setHeader("CamelHttpResponseCode", exchange.getIn().getHeader("CamelHttpResponseCode"));
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse((String) exchange.getIn().getBody());
			exchange.getOut().setBody(gson.toJson(jsonElement));
			exchange.getOut().setHeader("content-type", ContentType.APPLICATION_JSON);
		} catch(Exception e) {
			exchange.getOut().setBody(exchange.getIn().getBody());
			exchange.getOut().setHeader("content-type", ContentType.TEXT_PLAIN);
		}
    }
}
