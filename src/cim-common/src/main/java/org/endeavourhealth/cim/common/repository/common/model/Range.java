package org.endeavourhealth.cim.common.repository.common.model;

/// <summary>
/// Set of values bounded by low and high
/// </summary>
public class Range {
    /// <summary>
    /// Low limit
    /// </summary>
    public Quantity low;

    /// <summary>
    /// High limit
    /// </summary>
    public Quantity high;

    public void setLow(Quantity low) {
        this.low = low;
    }

    public Quantity getLow() {
        return low;
    }

    public void setHigh(Quantity high) {
        this.high = high;
    }

    public Quantity getHigh() {
        return high;
    }
}
