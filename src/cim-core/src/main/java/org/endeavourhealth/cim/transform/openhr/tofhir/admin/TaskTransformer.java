package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.valueSets.TaskStatusCode;
import org.endeavourhealth.cim.transform.common.valueSets.TaskTypeCode;
import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001PatientTask;
import org.endeavourhealth.cim.transform.schemas.openhr.VocTaskPriority;
import org.endeavourhealth.cim.transform.schemas.openhr.VocTaskStatus;
import org.endeavourhealth.cim.transform.schemas.openhr.VocTaskType;
import org.endeavourhealth.cim.transform.common.valueSets.TaskPriorityCode;
import org.hl7.fhir.instance.model.*;


public class TaskTransformer {
	public final static String TASKTYPE_EXTENSION_URL = "http://cim-api.endeavourhealth.org/fhir/ValueSet/task-type";
	public final static String TASKTYPE_SYSTEM = "http://cim-api.endeavourhealth.org/fhir/task-type";
	public final static String TASKPRIORITY_EXTENSION_URL = "http://cim-api.endeavourhealth.org/fhir/ValueSet/task-priority";
	public final static String TASKPRIORITY_SYSTEM = "http://cim-api.endeavourhealth.org/fhir/task-priority";
	public final static String TASKSTATUS_EXTENSION_URL = "http://cim-api.endeavourhealth.org/fhir/ValueSet/task-status";
	public final static String TASKSTATUS_SYSTEM = "http://cim-api.endeavourhealth.org/fhir/task-status";

	public static Order transform(OpenHR001PatientTask source) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException
	{
		ToFHIRHelper.ensureDboNotDelete(source);

		Order target = new Order();
		target.setId(source.getId());
		target.setDate(TransformHelper.toDate(source.getCreatedDate()));
		if (source.getPatient() != null && !source.getPatient().isEmpty())
			target.setSubject(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));

		target.setSource(ReferenceHelper.createReference(ResourceType.Practitioner, source.getCreatingUserInRole()));
		for (String targetPractitioner : source.getOwningUserInRole())
			target.setTarget(ReferenceHelper.createReference(ResourceType.Practitioner, targetPractitioner));


		target.setReason(new CodeableConcept().addCoding().setDisplay(source.getSubject()));

		Order.OrderWhenComponent when = new Order.OrderWhenComponent();
		Timing timing = new Timing();
		timing.addEvent(TransformHelper.toDate(source.getActionByDate()));
		when.setSchedule(timing);
		target.setWhen(when);

		target.addDetail().setDisplay(source.getDescription());

		addTaskTypeExtension(source.getTaskType(), target);
		addTaskPriorityExtension(source.getPriority(), target);
		addTaskStatusExtension(source.getStatus(), target);

		return target;
	}

	private static void addTaskTypeExtension(VocTaskType taskType, Order target) {
		if (taskType == null)
			return;

		target.addExtension(
				new Extension()
						.setUrl(TASKTYPE_EXTENSION_URL)
						.setValue(new CodeableConcept()
								.addCoding(new Coding()
										.setSystem(TASKTYPE_SYSTEM)
										.setCode(toFhirTaskType(taskType).name())
										.setDisplay(taskType.toString()))));
	}

	private static void addTaskPriorityExtension(VocTaskPriority taskPriority, Order target) {
		if (taskPriority == null)
			return;

		target.addExtension(
				new Extension()
						.setUrl(TASKPRIORITY_EXTENSION_URL)
						.setValue(new CodeableConcept()
								.addCoding(new Coding()
										.setSystem(TASKPRIORITY_SYSTEM)
										.setCode(toFhirTaskPriority(taskPriority).name())
										.setDisplay(taskPriority.toString()))));
	}

	private static void addTaskStatusExtension(VocTaskStatus taskStatus, Order target) {
		if (taskStatus == null)
			return;

		target.addExtension(
				new Extension()
						.setUrl(TASKSTATUS_EXTENSION_URL)
						.setValue(new CodeableConcept()
								.addCoding(new Coding()
										.setSystem(TASKSTATUS_SYSTEM)
										.setCode(toFhirTaskStatus(taskStatus).name())
										.setDisplay(taskStatus.toString()))));
	}

	private static TaskTypeCode toFhirTaskType(VocTaskType taskType) {
		switch (taskType) {
			case BOOK_APPOINTMENT:
			case BOOK_APPOINTMENT_DOCTOR:
			case BOOK_APPOINTMENT_NURSE:
				return TaskTypeCode.bookAppointment;
			case TELEPHONE_PATIENT: return TaskTypeCode.telephonePatient;
			case SCREEN_MESSAGE: return TaskTypeCode.screenMessage;
			case RESULTS_FOR_INFO: return TaskTypeCode.resultsForInfo;
			case MEETING_NOTIFICATION: return TaskTypeCode.meetingNotification;
			case PATIENT_NOTE: return TaskTypeCode.patientNote;
			case ADMIN_NOTE: return TaskTypeCode.adminNote;
			case FORM_TO_COMPLETE: return TaskTypeCode.formToComplete;
			case REPEAT_TEST: return TaskTypeCode.repeatTest;
			case ESCALATION_NOTIFICATION: return TaskTypeCode.escalationNotification;
			case CONFIDENTIALITY_POLICIES_OVERRIDDEN: return TaskTypeCode.confidentialityOverridden;
			case DTS_TRANSMISSION_FAILURE: return TaskTypeCode.transmissionFailure;
			case LEGITIMATE_RELATIONSHIP_NOTIFICATION: return TaskTypeCode.legitimateRelationshipNotification;
			case DTS_TRANSMISSION_REPORT: return TaskTypeCode.transmissionReport;
			case OVERDUE_TASK_NOTIFICATION: return TaskTypeCode.overdueTaskNotification;
			default: throw new IllegalArgumentException("Unknown task type " + taskType.name());
		}
	}

	private static TaskPriorityCode toFhirTaskPriority(VocTaskPriority taskPriority) {
		switch (taskPriority) {
			case HIGH: return TaskPriorityCode.high;
			case LOW: return TaskPriorityCode.low;
			case NORMAL: return TaskPriorityCode.medium;
			default: throw new IllegalArgumentException("Unknown task priority " + taskPriority.name());
		}
	}

	private static TaskStatusCode toFhirTaskStatus(VocTaskStatus taskStatus) {
		switch (taskStatus) {
			case ACTIVE: return TaskStatusCode.active;
			case COMPLETE: return TaskStatusCode.complete;
			case DELETED: return TaskStatusCode.deleted;
			case ARCHIVED: return TaskStatusCode.archived;
			default: throw new IllegalArgumentException("Unknown task status " + taskStatus.name());
		}
	}
}