CREATE COLUMNFAMILY cim.apikey
(
	apikey VARCHAR PRIMARY KEY,
	secret VARCHAR,
	userdata VARCHAR,
	userdata_schema_version VARCHAR
);