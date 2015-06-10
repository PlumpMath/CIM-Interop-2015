package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;

import java.util.*;

public class HeaderValidationProcessor implements org.apache.camel.Processor {
    private Map<String, List<String>> _legitimateRelationships;

    public HeaderValidationProcessor() {
        _legitimateRelationships = new HashMap<>();
        _legitimateRelationships.put("swagger", Arrays.asList("A99999", "B99999"));
        _legitimateRelationships.put("subsidiary", Arrays.asList("Y99999", "Z99999"));
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String odsCode = (String)exchange.getIn().getHeader("odsCode");

        if (odsCode != null) {
            ValidateLegitimateRelationships(exchange, odsCode);
        }
    }

    private void ValidateLegitimateRelationships(Exchange exchange, String odsCode) throws Exception {
        String api_key = (String) exchange.getIn().getHeader("api_key");

        List<String> validOrganisations = _legitimateRelationships.get(api_key);

        if (validOrganisations == null)
			throw new Exception("No legitimate relationships configured for this subsidiary system");

        if (validOrganisations.contains(odsCode) == false)
			throw new Exception("Subsidiary system has no legitimate relationship with this organisation");
    }
}
