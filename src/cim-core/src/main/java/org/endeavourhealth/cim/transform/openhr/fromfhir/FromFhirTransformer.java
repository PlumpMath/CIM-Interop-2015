package org.endeavourhealth.cim.transform.openhr.fromfhir;

import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.fromfhir.ConditionTransformer;
import org.endeavourhealth.cim.transform.openhr.fromfhir.OpenHRContainer;
import org.endeavourhealth.cim.transform.openhr.fromfhir.TaskTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Order;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Date;
import java.util.UUID;

public class FromFhirTransformer
{
    public OpenHR001OpenHealthRecord transformFromCondition(Condition condition) throws TransformException {
        OpenHRContainer container = new OpenHRContainer();
        ConditionTransformer.transform(container, condition);
        return createOpenHRFromContainer(container);
    }

	public OpenHR001OpenHealthRecord transformFromTask(Order task) throws TransformException {
		OpenHRContainer container = new OpenHRContainer();
		TaskTransformer.transform(container, task);
		return createOpenHRFromContainer(container);
	}

    private OpenHR001OpenHealthRecord createOpenHRFromContainer(OpenHRContainer container) throws TransformException {
        OpenHR001OpenHealthRecord openHR = new OpenHR001OpenHealthRecord();
        openHR.setId(UUID.randomUUID().toString());

        try {
            openHR.setCreationTime(TransformHelper.fromDate(new Date()));
        } catch (DatatypeConfigurationException e) {
            throw new SourceDocumentInvalidException("Error creating Date", e);
        }

        OpenHR001AuthorSystem authorSystem = new OpenHR001AuthorSystem();
        authorSystem.setSystemType(VocMessageAuthorSystemType.EXT);
        OpenHR001MessageAuthor author = new OpenHR001MessageAuthor();
        author.setSystem(authorSystem);
        openHR.setAuthor(author);

        OpenHR001ContentSpecification contentSpecification = new OpenHR001ContentSpecification();
        contentSpecification.setSpecification("37231000000106");
        openHR.setContentSpecification(contentSpecification);

        OpenHR001HealthDomain healthDomain = new OpenHR001HealthDomain();

        for (OpenHR001HealthDomain.Event event: container.getEvents())
            healthDomain.getEvent().add(event);

        for (OpenHR001Problem problem: container.getProblems())
            healthDomain.getProblem().add(problem);

		for (OpenHR001PatientTask task: container.getPatientTasks())
			healthDomain.getTask().add(task);

        openHR.setHealthDomain(healthDomain);

        return openHR;
    }

}
