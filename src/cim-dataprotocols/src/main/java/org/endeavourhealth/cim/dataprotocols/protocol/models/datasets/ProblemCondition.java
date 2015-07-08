package org.endeavourhealth.cim.dataprotocols.protocol.models.datasets;

import org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.attributes.ProblemStatusAttribute;
import org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.attributes.ProblemTypeAttribute;
import org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.rules.DateRangeRule;

public class ProblemCondition extends Condition {
    private ProblemStatusAttribute problemStatusAttribute;
    private ProblemTypeAttribute problemTypeAttribute;
    private DateRangeRule dateRangeRule;

    @Override
    public ConditionType getType() { return ConditionType.prb; }


    public ProblemTypeAttribute getProblemTypeAttribute() {
        return problemTypeAttribute;
    }

    public void setProblemTypeAttribute(ProblemTypeAttribute problemTypeAttribute) {
        this.problemTypeAttribute = problemTypeAttribute;
    }

    public DateRangeRule getDateRangeRule() {
        return dateRangeRule;
    }

    public void setDateRangeRule(DateRangeRule dateRangeRule) {
        this.dateRangeRule = dateRangeRule;
    }

    public ProblemStatusAttribute getProblemStatusAttribute() {
        return problemStatusAttribute;
    }

    public void setProblemStatusAttribute(ProblemStatusAttribute problemStatusAttribute) {
        this.problemStatusAttribute = problemStatusAttribute;
    }
}
