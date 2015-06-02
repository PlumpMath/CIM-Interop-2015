package org.endeavourhealth.cim.processor.subscription;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.adapter.IDataAdapter;

public class ChangePollerRouteBuilder extends RouteBuilder {
    private final IDataAdapter _dataAdapter;
    private final String _routeId;

    public ChangePollerRouteBuilder(String routeId, IDataAdapter dataAdapter) {
        _dataAdapter = dataAdapter;
        _routeId = routeId;
    }

    @Override
    public void configure() throws Exception {

        from("timer://"+_routeId+"?fixedRate=true&period=10s")
            .routeId(_routeId);
            // .process(new GetPatientChanges(_dataAdapter));
    }
}
