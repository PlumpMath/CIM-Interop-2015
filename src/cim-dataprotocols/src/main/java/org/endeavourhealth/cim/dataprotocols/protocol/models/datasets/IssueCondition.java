package org.endeavourhealth.cim.dataprotocols.protocol.models.datasets;

import org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.attributes.PrescriptionTypeAttribute;
import org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.rules.DateRangeRule;

public class IssueCondition extends Condition {
    private PrescriptionTypeAttribute prescriptionTypeAttribute;
    private DateRangeRule dateRangeRule;

    @Override
    public ConditionType getType() { return ConditionType.iss; }


    public PrescriptionTypeAttribute getPrescriptionTypeAttribute() {
        return prescriptionTypeAttribute;
    }

    public void setPrescriptionTypeAttribute(PrescriptionTypeAttribute prescriptionTypeAttribute) {
        this.prescriptionTypeAttribute = prescriptionTypeAttribute;
    }

    public DateRangeRule getDateRangeRule() {
        return dateRangeRule;
    }

    public void setDateRangeRule(DateRangeRule dateRangeRule) {
        this.dateRangeRule = dateRangeRule;
    }
}
