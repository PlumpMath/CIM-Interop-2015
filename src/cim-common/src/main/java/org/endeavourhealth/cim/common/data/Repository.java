package org.endeavourhealth.cim.common.data;

import com.datastax.driver.core.Session;

public abstract class Repository {
    protected final Session session;
    protected final PreparedStatementCache statementCache;

    public Repository(String keyspace) {
        this.session = DbClient.getInstance().getSession(keyspace);
        this.statementCache = DbClient.getInstance().getStatementCache(this.session);
    }
}
