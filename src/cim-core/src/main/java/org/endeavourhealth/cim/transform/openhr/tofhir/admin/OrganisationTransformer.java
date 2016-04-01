package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.List;

public class OrganisationTransformer
{
	public static List<Organization> transform(List<OpenHR001Organisation> sources) throws TransformException
    {
        ArrayList<Organization> organizations = new ArrayList<>();

        for (OpenHR001Organisation source: sources)
            organizations.add(transform(source));

        return organizations;
    }

	public static Organization transform(OpenHR001Organisation source) throws TransformException
    {
		OpenHRHelper.ensureDboNotDelete(source);

		Organization target = new Organization();

        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_ORGANIZATION));

        if (StringUtils.isNotBlank(source.getNationalPracticeCode()))
            target.addIdentifier(TransformHelper.createIdentifier(FhirUris.IDENTIFIER_SYSTEM_ODS_CODE, source.getNationalPracticeCode()));

        target.setActive(source.getCloseDate() == null);

        if ((source.getOpenDate() != null) || (source.getCloseDate() != null))
        {
            Period period = new Period();

            if (source.getOpenDate() != null)
                period.setStart(TransformHelper.toDate(source.getOpenDate()));

            if (source.getCloseDate() != null)
                period.setEnd(TransformHelper.toDate(source.getCloseDate()));

            target.getActiveElement().addExtension(TransformHelper.createSimpleExtension(FhirUris.EXTENSION_URI_ACTIVEPERIOD, period));
        }

        target.setName(source.getName());
        target.setType(new CodeableConcept().setText(source.getOrganisationType().getDisplayName()));

        if (!StringUtils.isBlank(source.getParentOrganisation()))
            target.setPartOf(ReferenceHelper.createReference(ResourceType.Organization, source.getParentOrganisation()));

        if (!StringUtils.isBlank(source.getMainLocation()))
            target.addExtension(TransformHelper.createSimpleExtension(FhirUris.EXTENSION_URI_MAINLOCATION, ReferenceHelper.createReference(ResourceType.Location, source.getMainLocation())));

		return target;
	}
}