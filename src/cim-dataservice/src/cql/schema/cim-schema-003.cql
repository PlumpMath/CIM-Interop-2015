CREATE COLUMNFAMILY cim.service
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.service_by_organisation
(
  id VARCHAR PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.system
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.technical_interface
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.information_sharing_protocol
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.protocol_by_publisher_and_subscriber_agreement
(
  publisherAgreementId UUID,
  subscriberAgreementId UUID,
  schema_version VARCHAR,
  data VARCHAR,
  PRIMARY KEY (publisherAgreementId, subscriberAgreementId)
);

CREATE COLUMNFAMILY cim.sharing_agreement
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.agreement_by_service
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.publisher_profile
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.subscriber_profile
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.dataset_collection
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);

CREATE COLUMNFAMILY cim.dataset
(
  id UUID PRIMARY KEY,
  schema_version VARCHAR,
  data VARCHAR
);
