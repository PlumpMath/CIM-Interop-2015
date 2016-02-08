package org.endeavourhealth.cim.camel.helpers;

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

    @SuppressWarnings("unchecked")
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list;
        if (oldExchange == null) {
            list = new ArrayList<>();
            if (newBody != null || _includeNullResponses)
                list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = (ArrayList<Object>)oldExchange.getIn().getBody();
            if (newBody != null || _includeNullResponses)
                list.add(newBody);
            return oldExchange;
        }
    }
}