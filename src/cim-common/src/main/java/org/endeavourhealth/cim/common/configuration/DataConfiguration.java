package org.endeavourhealth.cim.common.configuration;

public class DataConfiguration {
    public static final String DATASERVICE_KEYSPACE = "eds";

    public static String[] getHosts() {
        return new String[] {"127.0.0.1", "192.168.145.135"};
    }
}
