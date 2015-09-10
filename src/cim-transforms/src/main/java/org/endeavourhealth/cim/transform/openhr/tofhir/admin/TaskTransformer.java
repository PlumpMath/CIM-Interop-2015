package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;


public class TaskTransformer {
	public final static String TASKTYPE_EXTENSION_URL = "http://www.e-mis.com/emisopen/extension/TaskType";
	public final static String TASKTYPE_SYSTEM = "http://www.e-mis.com/emisopen/TaskType";
	public final static String TASKPRIORITY_EXTENSION_URL = "http://www.e-mis.com/emisopen/extension/TaskPriority";
	public final static String TASKPRIORITY_SYSTEM = "http://www.e-mis.com/emisopen/TaskPriority";
	public final static String TASKSTATUS_EXTENSION_URL = "http://www.e-mis.com/emisopen/extension/TaskStatus";
	public final static String TASKSTATUS_SYSTEM = "http://www.e-mis.com/emisopen/TaskStatus";

	public static Order transform(OpenHR001PatientTask source) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException {
		ToFHIRHelper.ensureDboNotDelete(source);

		Order target = new Order();
		target.setId(source.getId());
		target.setDate(TransformHelper.toDate(source.getCreatedDate()));
		if (source.getPatient() != null && !source.getPatient().isEmpty())
			target.setSubject(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));

		target.setSource(ReferenceHelper.createReference(ResourceType.Practitioner, source.getCreatingUserInRole()));
		for (String targetPractitioner : source.getOwningUserInRole())
			target.setTarget(ReferenceHelper.createReference(ResourceType.Practitioner, targetPractitioner));

		target.setReason(ReferenceHelper.createReference(ResourceType.Basic, source.getSubject()));

		Order.OrderWhenComponent when = new Order.OrderWhenComponent();
		Timing timing = new Timing();
		timing.addEvent(TransformHelper.toDate(source.getActionByDate()));
		when.setSchedule(timing);
		target.setWhen(when);

		target.addDetail(ReferenceHelper.createReference(ResourceType.Basic, source.getDescription()));

		addTaskTypeExtension(source.getTaskType(), target);
		addTaskPriorityExtension(source.getPriority(), target);
		addTaskStatusExtension(source.getStatus(), target);

		return target;
	}

	private static void addTaskTypeExtension(VocTaskType taskType, Order target) {
		target.addExtension(
				new Extension()
						.setUrl(TASKTYPE_EXTENSION_URL)
						.setValue(new CodeableConcept()
								.addCoding(new Coding()
										.setSystem(TASKTYPE_SYSTEM)
										.setDisplay(taskType.toString()))));
	}

	private static void addTaskPriorityExtension(VocTaskPriority taskPriority, Order target) {
		target.addExtension(
				new Extension()
						.setUrl(TASKPRIORITY_EXTENSION_URL)
						.setValue(new CodeableConcept()
								.addCoding(new Coding()
										.setSystem(TASKPRIORITY_SYSTEM)
										.setDisplay(taskPriority.toString()))));
	}

	private static void addTaskStatusExtension(VocTaskStatus taskStatus, Order target) {
		target.addExtension(
				new Extension()
						.setUrl(TASKSTATUS_EXTENSION_URL)
						.setValue(new CodeableConcept()
								.addCoding(new Coding()
										.setSystem(TASKSTATUS_SYSTEM)
										.setDisplay(taskStatus.toString()))));
	}
}