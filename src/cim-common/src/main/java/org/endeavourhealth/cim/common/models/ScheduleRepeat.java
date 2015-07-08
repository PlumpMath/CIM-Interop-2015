package org.endeavourhealth.cim.common.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ScheduleRepeat {
    /// <summary>
    /// A unit of time (units from UCUM)
    /// </summary>
    public enum UnitsOfTime
    {
        S, // second.
        Min, // minute.
        H, // hour.
        D, // day.
        Wk, // week.
        Mo, // month.
        A // year.
    }

    /// <summary>
    /// Real world event that the schedule relates to
    /// </summary>
    public enum EventTiming
    {
        HS, // event occurs [duration] before the hour of sleep (or trying to).
        WAKE, // event occurs [duration] after waking.
        AC, // event occurs [duration] before a meal (from the Latin ante cibus).
        ACM, // event occurs [duration] before breakfast (from the Latin ante cibus matutinus).
        ACD, // event occurs [duration] before lunch (from the Latin ante cibus diurnus).
        ACV, // event occurs [duration] before dinner (from the Latin ante cibus vespertinus).
        PC, // event occurs [duration] after a meal (from the Latin post cibus).
        PCM, // event occurs [duration] after breakfast (from the Latin post cibus matutinus).
        PCD, // event occurs [duration] after lunch (from the Latin post cibus diurnus).
        PCV // event occurs [duration] after dinner (from the Latin post cibus vespertinus).
    }

    /// <summary>
    /// Event occurs frequency times per duration
    /// </summary>
    private Integer frequency;

    /// <summary>
    /// HS | WAKE | AC | ACM | ACD | ACV | PC | PCM | PCD | PCV - common life events
    /// </summary>
    private EventTiming when;

    /// <summary>
    /// Repeating or event-related duration
    /// </summary>
    private BigDecimal duration;

    /// <summary>
    /// s | min | h | d | wk | mo | a - unit of time (UCUM)
    /// </summary>
    private UnitsOfTime units;

    /// <summary>
    /// Number of times to repeat
    /// </summary>
    private Integer count;

    /// <summary>
    /// When to stop repeats
    /// </summary>
    private LocalDateTime end;

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setWhen(EventTiming when) {
        this.when = when;
    }

    public EventTiming getWhen() {
        return when;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setUnits(UnitsOfTime units) {
        this.units = units;
    }

    public UnitsOfTime getUnits() {
        return units;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}