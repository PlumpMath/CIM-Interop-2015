CREATE COLUMNFAMILY cim.audit 
(
    email TEXT,
    datetime TIMESTAMP,
    auditid UUID,
    direction VARCHAR,
    apikey TEXT,
    url VARCHAR,
    status VARCHAR,
    message TEXT,
    PRIMARY KEY (email, datetime, auditid, direction)
) WITH CLUSTERING ORDER BY (datetime DESC, auditid ASC, direction ASC);


