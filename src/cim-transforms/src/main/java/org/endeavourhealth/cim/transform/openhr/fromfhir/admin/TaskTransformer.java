package org.endeavourhealth.cim.transform.openhr.fromfhir.admin;

import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.fromfhir.OpenHRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import javax.xml.datatype.DatatypeConfigurationException;

public class TaskTransformer {
	public static void transform(OpenHRContainer container, Order task) throws SourceDocumentInvalidException {
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
		if (task.getReason() instanceof Reference)
			targetTask.setSubject(ReferenceHelper.getReferenceId((Reference) task.getReason(), ResourceType.Basic));

		try {
			targetTask.setActionByDate(TransformHelper.fromDate(task.getWhen().getSchedule().getEvent().get(0).getValue()));
		} catch (DatatypeConfigurationException e) {
			throw new SourceDocumentInvalidException("Error creating Action By Date", e);
		}

		if (task.getDetail().size() > 0)
			targetTask.setDescription(ReferenceHelper.getReferenceId(task.getDetail().get(0), ResourceType.Basic));

		for (Extension extension : task.getExtension()) {
			if (extension.hasValue()
					&& extension.getValue() instanceof CodeableConcept
					&& ((CodeableConcept)extension.getValue()).hasCoding()
					&& ((CodeableConcept)extension.getValue()).getCoding().size() > 0) {
				if (org.endeavourhealth.cim.transform.openhr.tofhir.admin.TaskTransformer.TASKTYPE_EXTENSION_URL.equals(extension.getUrl()))
					targetTask.setTaskType(VocTaskType.fromValue(((CodeableConcept) extension.getValue()).getCoding().get(0).getDisplay()));
				if (org.endeavourhealth.cim.transform.openhr.tofhir.admin.TaskTransformer.TASKPRIORITY_EXTENSION_URL.equals(extension.getUrl()))
					targetTask.setPriority(VocTaskPriority.fromValue(((CodeableConcept) extension.getValue()).getCoding().get(0).getDisplay()));
				if (org.endeavourhealth.cim.transform.openhr.tofhir.admin.TaskTransformer.TASKSTATUS_EXTENSION_URL.equals(extension.getUrl()))
					targetTask.setStatus(VocTaskStatus.fromValue(((CodeableConcept) extension.getValue()).getCoding().get(0).getDisplay()));
			}
		}

		container.getPatientTasks().add(targetTask);
	}
}