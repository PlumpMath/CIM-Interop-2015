package org.endeavourhealth.cim.common.data;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;

import java.util.concurrent.ConcurrentHashMap;

public class DbClient {
    private static DbClient instance = new DbClient();

    private final Cluster cluster;
    private final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Session, PreparedStatementCache> statementCaches = new ConcurrentHashMap<>();

    private DbClient() {
        cluster = Cluster.builder()
                .addContactPoints(DataConfiguration.getHosts())
				.withCredentials(DataConfiguration.getUsername(), DataConfiguration.getPassword())
                .build();
    }

    public Session getSession(String keyspace) {
        return sessions.computeIfAbsent(keyspace, key -> cluster.connect(key));
    }

    public PreparedStatementCache getStatementCache(Session session) {
        return statementCaches.computeIfAbsent(session, key -> new PreparedStatementCache(key));
    }

    public static DbClient getInstance() {
        return instance;
    }

    public void close() {
        //clear cached statements
        for(PreparedStatementCache cache : statementCaches.values()) {
            cache.clear();
        }
        statementCaches.clear();

        //close sessions
        for(Session session : sessions.values()) {
            session.close();
        }
        sessions.clear();

        //close cluster
        cluster.close();
    }
}