package org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.attributes;

import java.util.List;
import java.util.UUID;

public class CodeClusterAttribute {
    private boolean inclusive;
    private List<UUID> codeClusterIds;

    public boolean isInclusive() {
        return inclusive;
    }

    public void setInclusive(boolean inclusive) {
        this.inclusive = inclusive;
    }

    public List<UUID> getCodeClusterIds() {
        return codeClusterIds;
    }

    public void setCodeClusterIds(List<UUID> codeClusterIds) {
        this.codeClusterIds = codeClusterIds;
    }
}
