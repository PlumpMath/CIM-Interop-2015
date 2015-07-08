package org.endeavourhealth.cim.common.models;

/// <summary>
/// Time range defined by start and end date/time
/// </summary>
public class Period {
    /// <summary>
    /// Starting time with inclusive boundary
    /// </summary>
    public PartialDateTime start;
    /// <summary>
    /// End time with inclusive boundary, if not ongoing
    /// </summary>
    public PartialDateTime end;

    public void setStart(PartialDateTime start) {
        this.start = start;
    }

    public PartialDateTime getStart() {
        return start;
    }

    public void setEnd(PartialDateTime end) {
        this.end = end;
    }

    public PartialDateTime getEnd() {
        return end;
    }
}