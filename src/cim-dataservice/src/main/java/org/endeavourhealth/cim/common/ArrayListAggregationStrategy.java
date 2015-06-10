package org.endeavourhealth.cim.common;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

public class ArrayListAggregationStrategy implements AggregationStrategy {
    private boolean _includeNullResponses = false;
    public ArrayListAggregationStrategy() {
        _includeNullResponses = false;
    }

    public ArrayListAggregationStrategy(boolean includeNullResponses) {
        _includeNullResponses = includeNullResponses;
    }

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list = null;
        if (oldExchange == null) {
            list = new ArrayList<Object>();
            if (newBody != null || _includeNullResponses)
                list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            if (newBody != null || _includeNullResponses)
                list.add(newBody);
            return oldExchange;
        }
    }
}