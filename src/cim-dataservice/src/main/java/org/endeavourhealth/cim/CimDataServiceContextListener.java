package org.endeavourhealth.cim;

import org.endeavourhealth.core.repository.common.data.DbClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CimDataServiceContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("Starting up!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.print("Closing Cassandra connections...");
		DbClient dbClient = DbClient.getInstance();
		if (dbClient != null)
			dbClient.close();

		System.out.println("...done.");
	}
}