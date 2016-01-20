// Drop and recreate the keyspace
DROP KEYSPACE IF EXISTS ehr;
CREATE KEYSPACE ehr WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE ehr;

// Clinical Statements

// General

CREATE TABLE allergy_intolerance (
   allergy_intolerance_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, allergy_intolerance_id)
);
CREATE INDEX idx_allergy_intolerance_meta_data ON allergy_intolerance (ENTRIES(meta_data));

CREATE TABLE condition (
   condition_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, condition_id)
);
CREATE INDEX idx_condition_meta_data ON condition (ENTRIES(meta_data));

CREATE TABLE procedure (
   procedure_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, procedure_id)
);
CREATE INDEX idx_procedure_meta_data ON procedure (ENTRIES(meta_data));

CREATE TABLE clinical_impression (
   clinical_impression_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, clinical_impression_id)
);
CREATE INDEX idx_clinical_impression_meta_data ON clinical_impression (ENTRIES(meta_data));

CREATE TABLE family_member_history (
   family_member_history_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, family_member_history_id)
);
CREATE INDEX idx_family_member_history_meta_data ON family_member_history (ENTRIES(meta_data));

CREATE TABLE risk_assessment (
   risk_assessment_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, risk_assessment_id)
);
CREATE INDEX idx_risk_assessment_meta_data ON risk_assessment (ENTRIES(meta_data));

CREATE TABLE detected_issue (
   detected_issue_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, detected_issue_id)
);
CREATE INDEX idx_detected_issue_meta_data ON detected_issue (ENTRIES(meta_data));

// Care Provision

CREATE TABLE care_plan (
   care_plan_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, care_plan_id)
);
CREATE INDEX idx_care_plan_meta_data ON care_plan (ENTRIES(meta_data));

CREATE TABLE goal (
   goal_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, goal_id)
);
CREATE INDEX idx_goal_meta_data ON goal (ENTRIES(meta_data));

CREATE TABLE referral_request (
   referral_request_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, referral_request_id)
);
CREATE INDEX idx_referral_request_meta_data ON referral_request (ENTRIES(meta_data));

CREATE TABLE procedure_request (
   procedure_request_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, procedure_request_id)
);
CREATE INDEX idx_procedure_request_meta_data ON procedure_request (ENTRIES(meta_data));

CREATE TABLE nutrition_order (
   nutrition_order_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, nutrition_order_id)
);
CREATE INDEX idx_nutrition_order_meta_data ON nutrition_order (ENTRIES(meta_data));

CREATE TABLE vision_prescription (
   vision_prescription_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, vision_prescription_id)
);
CREATE INDEX idx_vision_prescription_meta_data ON vision_prescription (ENTRIES(meta_data));

// Medication & Immunization

CREATE TABLE medication (
   medication_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (medication_id)
);
CREATE INDEX idx_medication_meta_data ON medication (ENTRIES(meta_data));

CREATE TABLE medication_order (
   medication_order_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, medication_order_id)
);
CREATE INDEX idx_medication_order_meta_data ON medication_order (ENTRIES(meta_data));

CREATE TABLE medication_administration (
   medication_administration_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, medication_administration_id)
);
CREATE INDEX idx_medication_administration_meta_data ON medication_administration (ENTRIES(meta_data));

CREATE TABLE medication_dispense (
   medication_dispense_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, medication_dispense_id)
);
CREATE INDEX idx_medication_dispense_meta_data ON medication_dispense (ENTRIES(meta_data));

CREATE TABLE medication_statement (
   medication_statement_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, medication_statement_id)
);
CREATE INDEX idx_medication_statement_meta_data ON medication_statement (ENTRIES(meta_data));

CREATE TABLE immunization (
   immunization_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, immunization_id)
);
CREATE INDEX idx_immunization_meta_data ON immunization (ENTRIES(meta_data));

CREATE TABLE immunization_recommendation (
   immunization_recommendation_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, immunization_recommendation_id)
);
CREATE INDEX idx_immunization_recommendation_meta_data ON immunization_recommendation (ENTRIES(meta_data));

// Diagnostics

CREATE TABLE observation (
   observation_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, observation_id)
);
CREATE INDEX idx_observation_meta_data ON observation (ENTRIES(meta_data));

CREATE TABLE diagnostic_report (
   diagnostic_report_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, diagnostic_report_id)
);
CREATE INDEX idx_diagnostic_report_meta_data ON diagnostic_report (ENTRIES(meta_data));

CREATE TABLE diagnostic_order (
   diagnostic_order_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, diagnostic_order_id)
);
CREATE INDEX idx_diagnostic_order_meta_data ON diagnostic_order (ENTRIES(meta_data));

CREATE TABLE specimen (
   specimen_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, specimen_id)
);
CREATE INDEX idx_specimen_meta_data ON specimen (ENTRIES(meta_data));

CREATE TABLE body_site (
   body_site_id uuid,
   patient_id uuid,
   service_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, body_site_id)
);
CREATE INDEX idx_body_site_meta_data ON body_site (ENTRIES(meta_data));

CREATE TABLE imaging_study (
   imaging_study_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, imaging_study_id)
);
CREATE INDEX idx_imaging_study_meta_data ON imaging_study (ENTRIES(meta_data));

CREATE TABLE imaging_object_selection (
   imaging_object_selection_id uuid,
   patient_id uuid,
   service_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, imaging_object_selection_id)
);
CREATE INDEX idx_imaging_object_selection_meta_data ON imaging_object_selection (ENTRIES(meta_data));

// Identification Resources

// Individuals

CREATE TABLE patient (
   patient_id uuid,
   service_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (service_id, patient_id)
);
CREATE INDEX idx_patient_meta_data ON patient (ENTRIES(meta_data));

CREATE TABLE practitioner (
   practitioner_id uuid,
   service_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (service_id, practitioner_id)
);
CREATE INDEX idx_practitioner_meta_data ON practitioner (ENTRIES(meta_data));

CREATE TABLE related_person (
   related_person_id uuid,
   patient_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), related_person_id)
);
CREATE INDEX idx_related_person_meta_data ON related_person (ENTRIES(meta_data));

// Groups

CREATE TABLE organization (
   organization_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (organization_id)
);
CREATE INDEX idx_organization_meta_data ON organization (ENTRIES(meta_data));

CREATE TABLE health_care_service (
   health_care_service_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (health_care_service_id)
);
CREATE INDEX idx_health_care_service_meta_data ON health_care_service (ENTRIES(meta_data));

CREATE TABLE group (
   group_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (group_id)
);
CREATE INDEX idx_group_meta_data ON group (ENTRIES(meta_data));

// Entities

CREATE TABLE location (
   location_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (location_id)
);
CREATE INDEX idx_location_meta_data ON location (ENTRIES(meta_data));

CREATE TABLE substance (
   substance_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (substance_id)
);
CREATE INDEX idx_substance_meta_data ON substance (ENTRIES(meta_data));

CREATE TABLE person (
   person_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (person_id)
);
CREATE INDEX idx_person_meta_data ON person (ENTRIES(meta_data));

CREATE TABLE contract (
   contract_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (contract_id)
);
CREATE INDEX idx_contract_meta_data ON contract (ENTRIES(meta_data));

// Devices

CREATE TABLE device (
   device_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (device_id)
);
CREATE INDEX idx_device_meta_data ON device (ENTRIES(meta_data));

CREATE TABLE device_component (
   device_component_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (device_component_id)
);
CREATE INDEX idx_device_component_meta_data ON device_component (ENTRIES(meta_data));

CREATE TABLE device_metric (
   device_metric_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (device_metric_id)
);

CREATE INDEX idx_device_metric_meta_data ON device_metric (ENTRIES(meta_data));

// Workflow Resources

// Patient Management

CREATE TABLE encounter (
   encounter_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, encounter_id)
);
CREATE INDEX idx_encounter_meta_data ON encounter (ENTRIES(meta_data));

CREATE TABLE episode_of_care (
   episode_of_care_id uuid,
   patient_id uuid,
   service_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, episode_of_care_id)
);
CREATE INDEX idx_episode_of_care_meta_data ON episode_of_care (ENTRIES(meta_data));

CREATE TABLE communication (
   communication_id uuid,
   sender_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (sender_id, communication_id)
);
CREATE INDEX idx_communication_meta_data ON communication (ENTRIES(meta_data));

CREATE TABLE flag (
   flag_id uuid,
   patient_id uuid,
   service_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, flag_id)
);
CREATE INDEX idx_flag_meta_data ON flag (ENTRIES(meta_data));

// Scheduling

CREATE TABLE appointment (
   appointment_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (appointment_id)
);
CREATE INDEX idx_appointment_meta_data ON appointment (ENTRIES(meta_data));

CREATE TABLE appointment_response (
   appointment_response_id uuid,
   appointment_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (appointment_id, appointment_response_id)
);
CREATE INDEX idx_appointment_response_meta_data ON appointment_response (ENTRIES(meta_data));

CREATE TABLE schedule (
   schedule_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (schedule_id)
);
CREATE INDEX idx_schedule_meta_data ON schedule (ENTRIES(meta_data));

CREATE TABLE slot (
   slot_id uuid,
   schedule_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (schedule_id, slot_id)
);
CREATE INDEX idx_slot_meta_data ON slot (ENTRIES(meta_data));

// Workflow #1

CREATE TABLE orders (
   order_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, order_id)
);
CREATE INDEX idx_orders_meta_data ON orders (ENTRIES(meta_data));

CREATE TABLE order_response (
   order_response_id uuid,
   order_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (order_id, order_response_id)
);
CREATE INDEX idx_order_response_meta_data ON order_response (ENTRIES(meta_data));

CREATE TABLE communication_request (
   communication_request_id uuid,
   sender_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (sender_id, communication_request_id)
);
CREATE INDEX idx_communication_request_meta_data ON communication_request (ENTRIES(meta_data));

CREATE TABLE device_use_request (
   device_use_request_id uuid,
   device_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (device_id, device_use_request_id)
);
CREATE INDEX idx_device_use_request_meta_data ON device_use_request (ENTRIES(meta_data));

CREATE TABLE device_use_statement (
   device_use_statement_id uuid,
   device_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (device_id, device_use_statement_id)
);
CREATE INDEX idx_device_use_statement_meta_data ON device_use_statement (ENTRIES(meta_data));

// Workflow #2

CREATE TABLE process_request (
   process_request_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (process_request_id)
);
CREATE INDEX idx_process_request_meta_data ON process_request (ENTRIES(meta_data));

CREATE TABLE process_response (
   process_response_id uuid,
   process_request_id uuid,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY (process_request_id, process_response_id)
);
CREATE INDEX idx_process_response_meta_data ON process_response (ENTRIES(meta_data));

CREATE TABLE supply_request (
   supply_request_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, supply_request_id)
);
CREATE INDEX idx_supply_request_meta_data ON supply_request (ENTRIES(meta_data));

CREATE TABLE supply_delivery (
   supply_delivery_id uuid,
   patient_id uuid,
   service_id uuid,
   effective_datetime timestamp,
   meta_data map<text,text>,
   entry_data text,

   last_updated timestamp,

   PRIMARY KEY ((patient_id), service_id, effective_datetime, supply_delivery_id)
);
CREATE INDEX idx_supply_delivery_meta_data ON supply_delivery (ENTRIES(meta_data));

// examples

SELECT * FROM observation
	WHERE patient_id = 11af0e7f-be18-431e-9be9-fd1adb2f0742;

SELECT * FROM observation
	WHERE patient_id = 11af0e7f-be18-431e-9be9-fd1adb2f0742
	AND service_id = 8484e105-332e-4b1a-9614-406c7c84662b;

SELECT * FROM observation
    WHERE patient_id = 11af0e7f-be18-431e-9be9-fd1adb2f0742
    AND service_id = 24c33aad-92bd-41d8-a622-ab88e00b97d6
    AND meta_data['id'] = '75f25bf1-d121-4504-9464-6ed6172bf278';

SELECT * FROM observation
    WHERE patient_id = 11af0e7f-be18-431e-9be9-fd1adb2f0742
    AND service_id = 24c33aad-92bd-41d8-a622-ab88e00b97d6
    AND effective_datetime > '1999-01-01';

SELECT * FROM observation
    WHERE patient_id = 11af0e7f-be18-431e-9be9-fd1adb2f0742
    AND service_id = 24c33aad-92bd-41d8-a622-ab88e00b97d6
    AND effective_datetime > '1999-01-01'
    AND meta_data['code'] = '82272006' ;

SELECT * FROM observation
    WHERE patient_id = 11af0e7f-be18-431e-9be9-fd1adb2f0742
    AND service_id = 24c33aad-92bd-41d8-a622-ab88e00b97d6
    AND meta_data['code'] = '82272006' ;

SELECT * FROM condition
	WHERE patient_id = 11af0e7f-be18-431e-9be9-fd1adb2f0742
	AND service_id = 8484e105-332e-4b1a-9614-406c7c84662b
	AND meta_data['severity'] = 'severe'
	AND meta_data['status'] = 'current' ALLOW FILTERING;

SELECT * FROM condition
	WHERE patient_id = 11af0e7f-be18-431e-9be9-fd1adb2f0742
	AND service_id = 8484e105-332e-4b1a-9614-406c7c84662b
	AND meta_data['code'] = '82272006'
	AND meta_data['severity'] = 'severe'
	ALLOW FILTERING;

select * from medication_order
	WHERE patient_id = 11af0e7f-be18-431e-9be9-fd1adb2f0742
	AND service_id = 8484e105-332e-4b1a-9614-406c7c84662b
	AND meta_data['status'] = 'current';

