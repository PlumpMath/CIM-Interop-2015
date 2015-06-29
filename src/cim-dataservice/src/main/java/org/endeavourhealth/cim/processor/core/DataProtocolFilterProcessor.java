package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class DataProtocolFilterProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        // TODO: Data protocol filtering

        // Retrieve DP from exchange

        // Apply filtering (inclusion/exclusion codes/data sets, etc)
    }
}
