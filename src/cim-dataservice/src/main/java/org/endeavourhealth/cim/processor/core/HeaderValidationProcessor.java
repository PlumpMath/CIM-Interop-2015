package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;

import java.util.*;

public class HeaderValidationProcessor implements org.apache.camel.Processor {
    private Map<String, List<String>> _legitimateRelationships;

    public HeaderValidationProcessor() {
        _legitimateRelationships = new HashMap<>();
        _legitimateRelationships.put("swagger", Arrays.asList("Z99999", "A99999"));
        _legitimateRelationships.put("subsidiary", Arrays.asList("Z99999", "A99999"));
        _legitimateRelationships.put("noLR", Arrays.asList("X00000"));
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String api_key = (String)exchange.getIn().getHeader("api_key");
        String serviceId = (String)exchange.getIn().getHeader("serviceId");

        List<String> validOrganisations = _legitimateRelationships.get(api_key);

        if (validOrganisations == null)
            throw new Exception("No legitimate relationships configured for this subsidiary system");

        if (validOrganisations.contains(serviceId) == false)
            throw new Exception("Subsidiary system has no legitimate relationship with this organisation");
    }
}
