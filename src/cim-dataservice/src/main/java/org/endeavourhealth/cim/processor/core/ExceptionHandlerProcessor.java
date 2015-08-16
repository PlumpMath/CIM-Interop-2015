package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.exceptions.CIMException;
import org.endeavourhealth.cim.common.exceptions.FallbackExceptionMapper;

public class ExceptionHandlerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        CIMException cimException = FallbackExceptionMapper.getCIMException(exception);

        ExchangeHelper.setInHeaderString(exchange, Exchange.HTTP_RESPONSE_CODE, cimException.getHttpStatus());
        ExchangeHelper.setInBodyString(exchange, cimException.getOperationOutcomeAsJson());
    }
}
