CREATE COLUMNFAMILY cim.rabbit_config
(
	channelName VARCHAR PRIMARY KEY,
	usernamePassword VARCHAR,
	uri VARCHAR,
	data_schema_version VARCHAR
);
