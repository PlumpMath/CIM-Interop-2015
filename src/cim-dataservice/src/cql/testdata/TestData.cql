USE cim;

--  ******************** Common/Core data ********************

-- Mock GP System
INSERT INTO technical_interface
(id, schema_version, data)
VALUES
(a4cfa902-3de1-42db-99ad-105b31027164, '0.1', '{ "id": "a4cfa902-3de1-42db-99ad-105b31027164", "description": "Mock GP System", "enabled": "true", "messageType": "SOAP", "messageFormat": "OpenHR, EMISOpen", "messageFormatVersion": "???", "deliveryEndpoint": "http://localhost:9001/GPApiService/Soap", "ackEndpoint": "http://localhost:9001/GPApiService/Soap" }');

INSERT INTO system
(id, schema_version, data)
VALUES
(3ee0dfc1-7df1-4d49-9145-2a21135f8f6e, '0.1', '{ "id": "3ee0dfc1-7df1-4d49-9145-2a21135f8f6e", "name": "Mock GP System", "enabled": "true", "technicalInterfaceId": "a4cfa902-3de1-42db-99ad-105b31027164" }');

-- Mock Secondary Care System
INSERT INTO technical_interface
(id, schema_version, data)
VALUES
(9451e75f-b110-4e89-84db-f8b37f4ed061, '0.1', '{ "id": "9451e75f-b110-4e89-84db-f8b37f4ed061", "description": "Mock Secondary System", "enabled": "true", "messageType": "REST", "messageFormat": "FHIR", "messageFormatVersion": "v0.5.0", "deliveryEndpoint": "http://localhost:9002/SecondaryCareApiService", "ackEndpoint": "http://localhost:9002/SecondaryCareApiService" }');

INSERT INTO system
(id, schema_version, data)
VALUES
(7a74b659-07ab-441e-8812-f37021ee235a, '0.1', '{ "id": "7a74b659-07ab-441e-8812-f37021ee235a", "name": "Mock Secondary System", "enabled": "true", "technicalInterfaceId": "9451e75f-b110-4e89-84db-f8b37f4ed061" }');

-- Swagger
INSERT INTO technical_interface
(id, schema_version, data)
VALUES
(3814881e-dc62-4917-97b4-4d9eb25de8a0, '0.1', '{ "id": "3814881e-dc62-4917-97b4-4d9eb25de8a0", "description": "Swagger", "enabled": "true", "messageType": "REST", "messageFormat": "FHIR", "messageFormatVersion": "v0.5.0", "deliveryEndpoint": "http://localhost:8080/cim", "ackEndpoint": "http://localhost:8080/cim" }');

INSERT INTO system
(id, schema_version, data)
VALUES
(482fb7f9-9e3d-4338-8193-c5eaebb77cb6, '0.1', '{ "id": "482fb7f9-9e3d-4338-8193-c5eaebb77cb6", "name": "Swagger", "enabled": "true", "technicalInterfaceId": "3814881e-dc62-4917-97b4-4d9eb25de8a0" }');


--  ******************** Basic (Mock) Agreement ********************

-- Mock GP System - Yankee service
INSERT INTO service
(id, schema_version, data)
VALUES
(312b80e2-2fa7-11e5-a151-feff819cdc9f, '0.1', '{ "id": "312b80e2-2fa7-11e5-a151-feff819cdc9f", "name": "Yankee", "enabled": "true", "serviceCategory": [1,2], "organisationId": "Y99999" }');

INSERT INTO service_by_organisation
(id, schema_version, data)
VALUES
('Y99999', '0.1', '{ "services": ["312b80e2-2fa7-11e5-a151-feff819cdc9f"] }');

INSERT INTO sharing_agreement
(id, schema_version, data)
VALUES
(2db67be1-e076-4003-92a5-b4764845295f, '0.1', '{ "id": "2db67be1-e076-4003-92a5-b4764845295f", "serviceId": "312b80e2-2fa7-11e5-a151-feff819cdc9f", "systemId": "3ee0dfc1-7df1-4d49-9145-2a21135f8f6e", "technicalInterfaceId": "a4cfa902-3de1-42db-99ad-105b31027164", "active": "true" }');

INSERT INTO agreement_by_service
(id, schema_version, data)
VALUES
(312b80e2-2fa7-11e5-a151-feff819cdc9f, '0.1', '{ "id": "312b80e2-2fa7-11e5-a151-feff819cdc9f", "agreements": ["2db67be1-e076-4003-92a5-b4764845295f"] }');

-- Mock GP System - Zulu service
INSERT INTO service
(id, schema_version, data)
VALUES
(3bcf2530-2fa7-11e5-a151-feff819cdc9f, '0.1', '{ "id": "3bcf2530-2fa7-11e5-a151-feff819cdc9f", "name": "Zulu", "enabled": "true", "serviceCategory": [1,2], "organisationId": "Z99999" }');

INSERT INTO service_by_organisation
(id, schema_version, data)
VALUES
('Z99999', '0.1', '{ "services": ["3bcf2530-2fa7-11e5-a151-feff819cdc9f"] }');

INSERT INTO sharing_agreement
(id, schema_version, data)
VALUES
(b94f0f26-9940-47dd-9598-2095402e163b, '0.1', '{ "id": "b94f0f26-9940-47dd-9598-2095402e163b", "serviceId": "3bcf2530-2fa7-11e5-a151-feff819cdc9f", "systemId": "3ee0dfc1-7df1-4d49-9145-2a21135f8f6e", "technicalInterfaceId": "a4cfa902-3de1-42db-99ad-105b31027164", "active": "true" }');

INSERT INTO agreement_by_service
(id, schema_version, data)
VALUES
(3bcf2530-2fa7-11e5-a151-feff819cdc9f, '0.1', '{ "id": "3bcf2530-2fa7-11e5-a151-feff819cdc9f", "agreements": ["b94f0f26-9940-47dd-9598-2095402e163b"] }');

-- Mock Secondary Care System
INSERT INTO service
(id, schema_version, data)
VALUES
(72471534-a166-459e-b71d-87a507a9647d, '0.1', '{ "id": "72471534-a166-459e-b71d-87a507a9647d", "name": "Subsidiary", "enabled": "true", "serviceCategory": [1,2], "organisationId": "subsidiary" }');

INSERT INTO service_by_organisation
(id, schema_version, data)
VALUES
('subsidiary', '0.1', '{ "services": ["72471534-a166-459e-b71d-87a507a9647d"] }');

INSERT INTO sharing_agreement
(id, schema_version, data)
VALUES
(4d57b6d3-8596-4835-9549-f86dd6947e82, '0.1', '{ "id": "4d57b6d3-8596-4835-9549-f86dd6947e82", "serviceId": "72471534-a166-459e-b71d-87a507a9647d", "systemId": "7a74b659-07ab-441e-8812-f37021ee235a", "technicalInterfaceId": "9451e75f-b110-4e89-84db-f8b37f4ed061", "active": "true" }');

INSERT INTO agreement_by_service
(id, schema_version, data)
VALUES
(72471534-a166-459e-b71d-87a507a9647d, '0.1', '{ "id": "72471534-a166-459e-b71d-87a507a9647d", "agreements": ["4d57b6d3-8596-4835-9549-f86dd6947e82"] }');

-- Information Sharing Protocol (Secondary <--> Yankee & Zulu)
INSERT INTO information_sharing_protocol
(id, schema_version, data)
VALUES
(a380671e-342a-4097-a16c-4de37a0720f8, '0.1', '
  {
    "id": "a380671e-342a-4097-a16c-4de37a0720f8",
    "name": "S1",
    "description": "basic agreement S1",
    "enabled": "true",
    "consentModel": "OptOut",
    "publisherProfiles": [],
    "subscriberProfiles": [],
    "publisherSharingAgreements": ["2db67be1-e076-4003-92a5-b4764845295f", "b94f0f26-9940-47dd-9598-2095402e163b"],
    "subscriberSharingAgreements": ["4d57b6d3-8596-4835-9549-f86dd6947e82"]
  }'
);

INSERT INTO protocol_by_publisher_and_subscriber_agreement
(publisherAgreementId, subscriberAgreementId, schema_version, data)
VALUES
(2db67be1-e076-4003-92a5-b4764845295f, 4d57b6d3-8596-4835-9549-f86dd6947e82, '0.1', '{ "publisherAgreementId": "2db67be1-e076-4003-92a5-b4764845295f", "subscriberAgreementId": "4d57b6d3-8596-4835-9549-f86dd6947e82" , "protocols": ["a380671e-342a-4097-a16c-4de37a0720f8"] }');

INSERT INTO protocol_by_publisher_and_subscriber_agreement
(publisherAgreementId, subscriberAgreementId, schema_version, data)
VALUES
(b94f0f26-9940-47dd-9598-2095402e163b, 4d57b6d3-8596-4835-9549-f86dd6947e82, '0.1', '{ "publisherAgreementId": "b94f0f26-9940-47dd-9598-2095402e163b", "subscriberAgreementId": "4d57b6d3-8596-4835-9549-f86dd6947e82", "protocols": ["a380671e-342a-4097-a16c-4de37a0720f8"] }');

--  ******************** Swagger agreement ********************

--  Mock GP System - Alpha service
INSERT INTO service
(id, schema_version, data)
VALUES
(5db9d66e-2fa6-11e5-a151-feff819cdc9f, '0.1', '{ "id": "5db9d66e-2fa6-11e5-a151-feff819cdc9f", "name": "Alpha", "enabled": "true", "serviceCategory": [1,2], "organisationId": "A99999" }');

INSERT INTO service_by_organisation
(id, schema_version, data)
VALUES
('A99999', '0.1', '{ "services": ["5db9d66e-2fa6-11e5-a151-feff819cdc9f"] }');

INSERT INTO sharing_agreement
(id, schema_version, data)
VALUES
(eeb6b511-bc3c-4ec5-9571-bcf9b6fc5cae, '0.1', '{ "id": "eeb6b511-bc3c-4ec5-9571-bcf9b6fc5cae", "serviceId": "5db9d66e-2fa6-11e5-a151-feff819cdc9f", "systemId": "3ee0dfc1-7df1-4d49-9145-2a21135f8f6e", "technicalInterfaceId": "a4cfa902-3de1-42db-99ad-105b31027164", "active": "true" }');

INSERT INTO agreement_by_service
(id, schema_version, data)
VALUES
(5db9d66e-2fa6-11e5-a151-feff819cdc9f, '0.1', '{ "id": "5db9d66e-2fa6-11e5-a151-feff819cdc9f", "agreements": ["eeb6b511-bc3c-4ec5-9571-bcf9b6fc5cae"] }');

--  Mock GP System - Bravo service
INSERT INTO service
(id, schema_version, data)
VALUES
(2680acc6-2fa7-11e5-a151-feff819cdc9f, '0.1', '{ "id": "2680acc6-2fa7-11e5-a151-feff819cdc9f", "name": "Bravo", "enabled": "true", "serviceCategory": [1,2], "organisationId": "B99999" }');

INSERT INTO service_by_organisation
(id, schema_version, data)
VALUES
('B99999', '0.1', '{ "services": ["2680acc6-2fa7-11e5-a151-feff819cdc9f"] }');

INSERT INTO sharing_agreement
(id, schema_version, data)
VALUES
(44ffdb19-3a38-4293-b3e8-1a897e4d72de, '0.1', '{ "id": "44ffdb19-3a38-4293-b3e8-1a897e4d72de", "serviceId": "2680acc6-2fa7-11e5-a151-feff819cdc9f", "systemId": "3ee0dfc1-7df1-4d49-9145-2a21135f8f6e", "technicalInterfaceId": "a4cfa902-3de1-42db-99ad-105b31027164", "active": "true" }');

INSERT INTO agreement_by_service
(id, schema_version, data)
VALUES
(2680acc6-2fa7-11e5-a151-feff819cdc9f, '0.1', '{ "id": "2680acc6-2fa7-11e5-a151-feff819cdc9f", "agreements": ["44ffdb19-3a38-4293-b3e8-1a897e4d72de"] }');

-- Swagger
INSERT INTO service
(id, schema_version, data)
VALUES
(c5f63042-a0dd-411e-b4b4-93532b3bb023, '0.1', '{ "id": "c5f63042-a0dd-411e-b4b4-93532b3bb023", "name": "Swagger", "enabled": "true", "serviceCategory": [1,2], "organisationId": "swagger" }');

INSERT INTO service_by_organisation
(id, schema_version, data)
VALUES
('swagger', '0.1', '{ "services": ["c5f63042-a0dd-411e-b4b4-93532b3bb023"] }');

INSERT INTO sharing_agreement
(id, schema_version, data)
VALUES
(8d05c8ba-b75d-4096-8252-1b912dbd1b91, '0.1', '{ "id": "8d05c8ba-b75d-4096-8252-1b912dbd1b91", "serviceId": "c5f63042-a0dd-411e-b4b4-93532b3bb023", "systemId": "482fb7f9-9e3d-4338-8193-c5eaebb77cb6", "technicalInterfaceId": "3814881e-dc62-4917-97b4-4d9eb25de8a0", "active": "true" }');

INSERT INTO agreement_by_service
(id, schema_version, data)
VALUES
(c5f63042-a0dd-411e-b4b4-93532b3bb023, '0.1', '{ "id": "c5f63042-a0dd-411e-b4b4-93532b3bb023", "agreements": ["8d05c8ba-b75d-4096-8252-1b912dbd1b91"] }');

-- Information Sharing Protocol (Swagger <--> Alpha & Bravo)
INSERT INTO information_sharing_protocol
(id, schema_version, data)
VALUES
(6618b41b-5dfd-40fe-a768-6efc062bd915, '0.1', '
  {
    "id": "6618b41b-5dfd-40fe-a768-6efc062bd915",
    "name": "S1",
    "description": "basic agreement S1",
    "enabled": "true",
    "consentModel": "OptOut",
    "publisherProfiles": [],
    "subscriberProfiles": [],
    "publisherSharingAgreements": ["eeb6b511-bc3c-4ec5-9571-bcf9b6fc5cae", "44ffdb19-3a38-4293-b3e8-1a897e4d72de"],
    "subscriberSharingAgreements": ["8d05c8ba-b75d-4096-8252-1b912dbd1b91"]

  }'
);

INSERT INTO protocol_by_publisher_and_subscriber_agreement
(publisherAgreementId, subscriberAgreementId, schema_version, data)
VALUES
(eeb6b511-bc3c-4ec5-9571-bcf9b6fc5cae, 8d05c8ba-b75d-4096-8252-1b912dbd1b91, '0.1', '{ "publisherAgreementId": "eeb6b511-bc3c-4ec5-9571-bcf9b6fc5cae", "subscriberAgreementId": "8d05c8ba-b75d-4096-8252-1b912dbd1b91", "protocols": ["6618b41b-5dfd-40fe-a768-6efc062bd915"] }');

INSERT INTO protocol_by_publisher_and_subscriber_agreement
(publisherAgreementId, subscriberAgreementId, schema_version, data)
VALUES
(44ffdb19-3a38-4293-b3e8-1a897e4d72de, 8d05c8ba-b75d-4096-8252-1b912dbd1b91, '0.1', '{ "publisherAgreementId": "44ffdb19-3a38-4293-b3e8-1a897e4d72de", "subscriberAgreementId": "8d05c8ba-b75d-4096-8252-1b912dbd1b91", "protocols": ["6618b41b-5dfd-40fe-a768-6efc062bd915"] }');

INSERT INTO config
(configname, data, schema_version)
VALUES
('rabbit', '{ "uri": "endeavour-cloud.cloudapp.net:5670", "usernamePassword": "username=azureuser&password=Azureuser123", "exchange": "Camel" }', '0.1');