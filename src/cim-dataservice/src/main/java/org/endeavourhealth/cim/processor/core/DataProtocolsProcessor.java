package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class DataProtocolsProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        // Obtain list of relevant protocols from header
        // (These are set in the header during HeaderValidation)
    }
}
