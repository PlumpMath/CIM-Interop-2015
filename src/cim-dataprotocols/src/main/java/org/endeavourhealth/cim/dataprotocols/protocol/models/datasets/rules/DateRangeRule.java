package org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.rules;

public class DateRangeRule {
    private int quantity;
    private DateRangeRuleUnit unit;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DateRangeRuleUnit getUnit() {
        return unit;
    }

    public void setUnit(DateRangeRuleUnit unit) {
        this.unit = unit;
    }
}
