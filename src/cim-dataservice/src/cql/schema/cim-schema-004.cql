CREATE COLUMNFAMILY cim.user
(
	apikey VARCHAR PRIMARY KEY,	
	secret VARCHAR,
	email VARCHAR,	
	data VARCHAR,
	data_schema_version VARCHAR
);

CREATE INDEX ON cim.user (email);
