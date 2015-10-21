package org.endeavourhealth.core.repository.common.model;

import java.math.BigDecimal;

/// <summary>
/// A measured or measurable amount
/// </summary>
public class Quantity {
    /// <summary>
    /// How the Quantity should be understood and represented
    /// </summary>
    public enum QuantityCompararator
    {
        LessThan, // The actual value is less than the given value.
        LessOrEqual, // The actual value is less than or equal to the given value.
        GreaterOrEqual, // The actual value is greater than or equal to the given value.
        GreaterThan // The actual value is greater than the given value.
    }

    /// <summary>
    /// Numerical value (with implicit precision)
    /// </summary>
    private BigDecimal value;
    /// <summary>
    /// < | <= | >= | > - how to understand the value
    /// </summary>
    private QuantityCompararator comparator;
    /// <summary>
    /// Unit representation
    /// </summary>
    private String units;

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setComparator(QuantityCompararator comparator) {
        this.comparator = comparator;
    }

    public QuantityCompararator getComparator() {
        return comparator;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getUnits() {
        return units;
    }
}