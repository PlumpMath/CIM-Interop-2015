package org.endeavourhealth.transform.common.valueSets;

public enum TaskTypeCode {
	bookAppointment,
	telephonePatient,
	screenMessage,
	resultsForInfo,
	meetingNotification,
	patientNote,
	adminNote,
	formToComplete,
	repeatTest,
	escalationNotification,
	confidentialityOverridden,
	transmissionFailure,
	legitimateRelationshipNotification,
	transmissionReport,
	overdueTaskNotification,
	other;

	public String value() {
		return name();
	}

	public static TaskTypeCode fromValue(String v) {
		return valueOf(v);
	}
}
