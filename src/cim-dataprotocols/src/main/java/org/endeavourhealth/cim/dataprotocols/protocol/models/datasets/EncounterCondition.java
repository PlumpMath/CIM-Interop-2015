package org.endeavourhealth.cim.dataprotocols.protocol.models.datasets;

import org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.attributes.CodeClusterAttribute;
import org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.rules.DateRangeRule;

public class EncounterCondition extends Condition {
    private CodeClusterAttribute codeClusterAttribute;
    private DateRangeRule dateRangeRule;

    @Override
    public ConditionType getType() { return ConditionType.enc; }


    public CodeClusterAttribute getCodeClusterAttribute() {
        return codeClusterAttribute;
    }

    public void setCodeClusterAttribute(CodeClusterAttribute codeClusterAttribute) {
        this.codeClusterAttribute = codeClusterAttribute;
    }

    public DateRangeRule getDateRangeRule() {
        return dateRangeRule;
    }

    public void setDateRangeRule(DateRangeRule dateRangeRule) {
        this.dateRangeRule = dateRangeRule;
    }
}
