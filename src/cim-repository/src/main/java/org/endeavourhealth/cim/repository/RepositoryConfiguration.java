package org.endeavourhealth.cim.repository;

public class RepositoryConfiguration
{
    public static final String DATASERVICE_KEYSPACE = "cim";

    public static String[] getHosts() {
        return new String[] { "127.0.0.1", "endeavour-cim.cloudapp.net" };
    }

	public static String getUsername() { return "cassandra"; }
	public static String getPassword() { return "cassandra"; }
}
