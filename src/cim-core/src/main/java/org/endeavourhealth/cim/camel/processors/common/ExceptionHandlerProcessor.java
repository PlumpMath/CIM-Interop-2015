package org.endeavourhealth.cim.camel.processors.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.exceptions.BaseException;
import org.endeavourhealth.cim.exceptions.FallbackExceptionMapper;

public class ExceptionHandlerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        BaseException baseException = FallbackExceptionMapper.getException(exception);

        ExchangeHelper.setInHeaderString(exchange, Exchange.HTTP_RESPONSE_CODE, baseException.getHttpStatus());
        ExchangeHelper.setInBodyString(exchange, baseException.getOperationOutcomeAsJson());
    }
}
