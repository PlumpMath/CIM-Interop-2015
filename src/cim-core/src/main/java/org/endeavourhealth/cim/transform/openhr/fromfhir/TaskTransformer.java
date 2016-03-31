package org.endeavourhealth.cim.transform.openhr.fromfhir;

import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.valueSets.TaskStatusCode;
import org.endeavourhealth.cim.transform.common.valueSets.TaskTypeCode;
import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.openhr.fromfhir.OpenHRContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.common.valueSets.TaskPriorityCode;
import org.hl7.fhir.instance.model.*;

import javax.xml.datatype.DatatypeConfigurationException;

public class TaskTransformer {
	public static void transform(OpenHRContainer container, Order task) throws SourceDocumentInvalidException
	{
		OpenHR001PatientTask targetTask = new OpenHR001PatientTask();

		targetTask.setUpdateMode(VocUpdateMode.ADD);

		targetTask.setId(task.getId());

		try {
			targetTask.setCreatedDate(TransformHelper.fromDate(task.getDate()));
		} catch (DatatypeConfigurationException e) {
			throw new SourceDocumentInvalidException("Error creating Created Date", e);
		}

		if (task.hasSubject())
			targetTask.setPatient(ReferenceHelper.getReferenceId(task.getSubject(), ResourceType.Patient));

		targetTask.setCreatingUserInRole(ReferenceHelper.getReferenceId(task.getSource(), ResourceType.Practitioner));
		targetTask.getOwningUserInRole().add(ReferenceHelper.getReferenceId(task.getTarget(), ResourceType.Practitioner));
		if (task.getReason() instanceof Coding)
			targetTask.setSubject(((Coding)task.getReason()).getDisplay());

		try {
			targetTask.setActionByDate(TransformHelper.fromDate(task.getWhen().getSchedule().getEvent().get(0).getValue()));
		} catch (DatatypeConfigurationException e) {
			throw new SourceDocumentInvalidException("Error creating Action By Date", e);
		}


		if (task.getDetail().size() > 0)
			targetTask.setDescription(task.getDetail().get(0).getDisplay());

		for (Extension extension : task.getExtension()) {
			if (extension.hasValue()
					&& extension.getValue() instanceof CodeableConcept
					&& ((CodeableConcept)extension.getValue()).hasCoding()
					&& ((CodeableConcept)extension.getValue()).getCoding().size() > 0) {
				Coding coding = ((CodeableConcept)extension.getValue()).getCoding().get(0);
				if (org.endeavourhealth.cim.transform.openhr.tofhir.admin.TaskTransformer.TASKTYPE_EXTENSION_URL.equals(extension.getUrl()))
					targetTask.setTaskType(fromFhirTaskType(coding.getCode(), coding.getDisplay()));
				if (org.endeavourhealth.cim.transform.openhr.tofhir.admin.TaskTransformer.TASKPRIORITY_EXTENSION_URL.equals(extension.getUrl()))
					targetTask.setPriority(fromFhirTaskPriority(coding.getCode()));
				if (org.endeavourhealth.cim.transform.openhr.tofhir.admin.TaskTransformer.TASKSTATUS_EXTENSION_URL.equals(extension.getUrl()))
					targetTask.setStatus(fromFhirTaskStatus(coding.getCode()));
			}
		}

		// Missing mandatory fields
		targetTask.setExpiryDate(targetTask.getActionByDate());
		targetTask.setCompletionDate(targetTask.getActionByDate());

		container.getPatientTasks().add(targetTask);
	}

	private static VocTaskType fromFhirTaskType(String taskType, String display) {
		switch (TaskTypeCode.fromValue(taskType)) {
			case bookAppointment:
				if (display.toUpperCase().contains("NURSE")) return VocTaskType.BOOK_APPOINTMENT_NURSE;
				if (display.toUpperCase().contains("DOCTOR")) return VocTaskType.BOOK_APPOINTMENT_DOCTOR;
				return VocTaskType.BOOK_APPOINTMENT;
			case telephonePatient: return VocTaskType.TELEPHONE_PATIENT;
			case screenMessage: return VocTaskType.SCREEN_MESSAGE;
			case resultsForInfo: return VocTaskType.RESULTS_FOR_INFO;
			case meetingNotification: return VocTaskType.MEETING_NOTIFICATION;
			case patientNote: return VocTaskType.PATIENT_NOTE;
			case adminNote: return VocTaskType.ADMIN_NOTE;
			case formToComplete: return VocTaskType.FORM_TO_COMPLETE;
			case repeatTest: return VocTaskType.REPEAT_TEST;
			case escalationNotification: return VocTaskType.ESCALATION_NOTIFICATION;
			case confidentialityOverridden: return VocTaskType.CONFIDENTIALITY_POLICIES_OVERRIDDEN;
			case transmissionFailure: return VocTaskType.DTS_TRANSMISSION_FAILURE;
			case legitimateRelationshipNotification: return VocTaskType.LEGITIMATE_RELATIONSHIP_NOTIFICATION;
			case transmissionReport: return VocTaskType.DTS_TRANSMISSION_REPORT;
			case overdueTaskNotification: return VocTaskType.OVERDUE_TASK_NOTIFICATION;
			default: throw new IllegalArgumentException("Unknown task type " + taskType);
		}
	}

	private static VocTaskPriority fromFhirTaskPriority(String taskPriority) {
		switch (TaskPriorityCode.fromValue(taskPriority)) {
			case low: return VocTaskPriority.LOW;
			case medium: return VocTaskPriority.NORMAL;
			case high: return VocTaskPriority.HIGH;
			default: throw new IllegalArgumentException("Unknown task priority " + taskPriority);
		}
	}

	private static VocTaskStatus fromFhirTaskStatus(String taskStatus) {
		switch (TaskStatusCode.fromValue(taskStatus)) {
			case active: return VocTaskStatus.ACTIVE;
			case complete: return VocTaskStatus.COMPLETE;
			case deleted: return VocTaskStatus.DELETED;
			case archived: return VocTaskStatus.ARCHIVED;
			default: throw new IllegalArgumentException("Unknown task status " + taskStatus);
		}
	}

}