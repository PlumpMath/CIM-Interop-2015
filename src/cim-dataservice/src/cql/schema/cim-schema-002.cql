CREATE COLUMNFAMILY cim.audit
(
	rowkey TIMESTAMP PRIMARY KEY,
	apikey TEXT,
	message VARCHAR
);