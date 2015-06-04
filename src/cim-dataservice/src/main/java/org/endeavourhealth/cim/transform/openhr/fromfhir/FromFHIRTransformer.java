package org.endeavourhealth.cim.transform.openhr.fromfhir;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.openhr.fromfhir.clinical.ConditionTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.Condition;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.UUID;

public class FromFHIRTransformer {
    public OpenHR001OpenHealthRecord transformFromCondition(Condition condition) throws TransformException {
        OpenHRContainer container = new OpenHRContainer();
        ConditionTransformer.transform(container, condition);
        return createOpenHRFromContainer(container);
    }

    private OpenHR001OpenHealthRecord createOpenHRFromContainer(OpenHRContainer container) {
        OpenHR001OpenHealthRecord openHR = new OpenHR001OpenHealthRecord();
        openHR.setId(UUID.randomUUID().toString());
//        openHR.setCreationTime(new XMLGregorianCalendar().);


        return openHR;
    }

}
