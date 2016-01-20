package org.endeavourhealth.core.repository;

public class DataConfiguration {
    public static final String DATASERVICE_KEYSPACE = "cim";
    public static final String DATASERVICE_EHR_KEYSPACE = "ehr";

    public static String[] getHosts() {
        return new String[] {"127.0.0.1"};
    }

	public static String getUsername() { return "cassandra"; }
	public static String getPassword() { return "cassandra"; }
}
