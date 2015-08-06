USE cim;

CREATE COLUMNFAMILY service
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY service_by_organisation
(
  id VARCHAR PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY system
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY technical_interface
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY information_sharing_protocol
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY protocol_by_publisher_and_subscriber_agreement
(
  publisherAgreementId UUID,
  subscriberAgreementId UUID,
  schema_version VARCHAR,
  data VARCHAR,
  PRIMARY KEY (publisherAgreementId, subscriberAgreementId)
);

CREATE COLUMNFAMILY sharing_agreement
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY agreement_by_service
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY publisher_profile
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY subscriber_profile
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY dataset_collection
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY dataset
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);
