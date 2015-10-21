package org.endeavourhealth.core.repository.common.model;

import java.util.List;

/// <summary>
/// A schedule that specifies an event that may occur multiple times
/// </summary>
public class Schedule
{
    /// <summary>
    /// When the event occurs
    /// </summary>
    private List<Period> event;

    /// <summary>
    /// Only if there is none or one event
    /// </summary>
    private ScheduleRepeat repeat;

    public void setEvent(List<Period> event) {
        this.event = event;
    }

    public List<Period> getEvent() {
        return event;
    }

    public void setRepeat(ScheduleRepeat repeat) {
        this.repeat = repeat;
    }

    public ScheduleRepeat getRepeat() {
        return repeat;
    }
}
