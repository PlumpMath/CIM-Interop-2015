package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CIMError implements Processor {
    private int _errorCode;
    private String _errorMessage;

    public CIMError(int errorCode, String errorMessage) {
        super();
        _errorCode = errorCode;
        _errorMessage = errorMessage;
    }

    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, _errorCode);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/plain");
        exchange.getIn().setBody(_errorMessage);
    }
}
