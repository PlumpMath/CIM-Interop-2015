package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.openhr.DtDbo;
import org.endeavourhealth.cim.transform.schemas.openhr.VocUpdateMode;

import java.util.UUID;

public class ToFHIRHelper {
    public static UUID parseUUID(String id) throws SourceDocumentInvalidException {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new SourceDocumentInvalidException("Could not parse UUID: " + id, e);
        }
    }

    public static void ensureDboNotDelete(DtDbo dbo) throws TransformFeatureNotSupportedException {
        ensureDboNotDelete(dbo.getUpdateMode());
    }

    public static void ensureDboNotDelete(VocUpdateMode updateMode) throws TransformFeatureNotSupportedException {
        if (updateMode == VocUpdateMode.DELETE)
            throw new TransformFeatureNotSupportedException("DBO type of Delete not supported");
    }

}
