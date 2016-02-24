package org.endeavourhealth.cim.camel.processors.common;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.PropertyKey;
import org.endeavourhealth.cim.repository.domains.audit.AuditRepository;
import org.endeavourhealth.cim.repository.utils.TextUtils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class AuditProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        try
        {
            String message = "";

            Object body = exchange.getIn().getBody();
            if (body instanceof String)
                message = (String) exchange.getIn().getBody();
            if (body instanceof InputStream)
                message = IOUtils.toString((InputStream) body);

            String url = getUrl(exchange);

            Object o = exchange.getIn().getHeader("CamelHttpResponseCode");

            UUID auditUuid = UUID.randomUUID();
            if (ExchangeHelper.hasProperty(exchange, PropertyKey.AuditId))
                auditUuid = ((UUID) exchange.getProperty(PropertyKey.AuditId));

            Date date = new Date();
            String apikey = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.ApiKey);

            String email = "UNKNOWN";
            if (ExchangeHelper.hasProperty(exchange, PropertyKey.Email))
                email = (String) exchange.getProperty(PropertyKey.Email);

            String direction = (String) exchange.getProperty(PropertyKey.TapLocation);
            String statusCode = null;

            if (direction.equals("Outbound"))
            {
                if (o != null)
                    statusCode = Integer.valueOf((int) o).toString();
                else
                    statusCode = "200";
            }

            AuditRepository auditRepository = new AuditRepository();
            auditRepository.add(auditUuid, date, apikey, direction, email, url, statusCode, message);

            exchange.getIn().setBody(Arrays.asList(date, message));
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private String getUrl(Exchange exchange) {
        String url = exchange.getIn().getHeader("CamelHttpMethod")
                + " "
                + exchange.getIn().getHeader("CamelHttpUri");

        String queryString = (String)exchange.getIn().getHeader("CamelHttpQuery");

        if (!TextUtils.isNullOrTrimmedEmpty(queryString))
            url += "?" + queryString;

        return url;
    }
}
