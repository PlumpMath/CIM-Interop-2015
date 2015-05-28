package org.endeavourhealth.cim.routes.builders;

import com.github.oscerd.component.cassandra.CassandraConstants;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;
import java.util.List;

public class CIMAudit extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        List<String> collAddr = new ArrayList<String>();
        collAddr.add("endeavour-cim.cloudapp.net");

        from("direct:CIMAudit")
            .routeId("CIMAudit")
            .setHeader(CassandraConstants.CASSANDRA_CONTACT_POINTS, constant(collAddr))
            .setBody(simple("INSERT INTO Audit (rowkey, message) VALUES ('${date:now:yyyy-MM-dd hh:mm:ss.SSSS}', '${body}');"))
            .to("cassandra:cassandraConnection?keyspace=CIM");
    }
}
