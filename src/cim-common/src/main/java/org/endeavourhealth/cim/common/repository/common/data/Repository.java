package org.endeavourhealth.cim.common.repository.common.data;

import com.datastax.driver.core.Session;

public abstract class Repository {
    private final String keyspace;
    private Session session;
    private PreparedStatementCache statementCache;

    public Repository(String keyspace) {
        this.keyspace = keyspace;
    }

    protected Session getSession() {
        if (session == null)
            session = DbClient.getInstance().getSession(keyspace);
        return session;
    }

    protected PreparedStatementCache getStatementCache() {
        if (statementCache == null) {
            statementCache = DbClient.getInstance().getStatementCache(getSession());
        }
        return statementCache;
    }
}
