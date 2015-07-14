package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.InformationSharingFramework.model.InformationSharingProtocol;
import org.endeavourhealth.cim.common.HeaderKey;

import java.util.List;

public class DataProtocolFilterProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        // TODO: Data protocol filtering

        // Retrieve DP from exchange
        List<InformationSharingProtocol> informationSharingProtocols = (List<InformationSharingProtocol>)exchange.getIn().getHeader(HeaderKey.InformationSharingProtocols);

        // Apply filtering (inclusion/exclusion codes/data sets, etc)
    }
}
