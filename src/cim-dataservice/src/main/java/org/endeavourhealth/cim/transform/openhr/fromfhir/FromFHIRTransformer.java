package org.endeavourhealth.cim.transform.openhr.fromfhir;

import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.fromfhir.clinical.ConditionTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.Condition;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Date;
import java.util.UUID;

public class FromFHIRTransformer {
    public OpenHR001OpenHealthRecord transformFromCondition(Condition condition) throws TransformException {
        OpenHRContainer container = new OpenHRContainer();
        ConditionTransformer.transform(container, condition);
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

        openHR.setHealthDomain(healthDomain);

        return openHR;
    }

}
