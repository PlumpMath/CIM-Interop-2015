CREATE COLUMNFAMILY cim.config
(
	configName VARCHAR PRIMARY KEY,
	configData VARCHAR,
	data_schema_version VARCHAR
);
