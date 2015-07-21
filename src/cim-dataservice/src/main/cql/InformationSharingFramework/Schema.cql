USE cim;

CREATE COLUMNFAMILY service
(
  KEY UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY service_by_organisation
(
  KEY VARCHAR PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY system
(
  KEY UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY technical_interface
(
  KEY UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY information_sharing_protocol
(
  KEY UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY sharing_agreement
(
  KEY UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY publisher_profile
(
  KEY UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY subscriber_profile
(
  KEY UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY dataset_collection
(
  KEY UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY dataset
(
  KEY UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);
