package org.endeavourhealth.cim.common.repository.common.model;

/// <summary>
/// A ratio of two Quantity values - a numerator and a denominator
/// </summary>
public class Ratio {
    /// <summary>
    /// Numerator value
    /// </summary>
    private Quantity numerator;
    /// <summary>
    /// Denominator value
    /// </summary>
    private Quantity denominator;

    public void setNumerator(Quantity numerator) {
        this.numerator = numerator;
    }

    public Quantity getNumerator() {
        return numerator;
    }

    public void setDenominator(Quantity denominator) {
        this.denominator = denominator;
    }

    public Quantity getDenominator() {
        return denominator;
    }
}