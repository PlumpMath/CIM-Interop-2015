package org.endeavourhealth.core.repository.common.model;

public class AgeOrRange {
    private Age age;
    private Range range;

    public void setAge(Age age) {
        //only valid to have either age or age range, never both
        if (range != null) {
            range = null;
        }
        this.age = age;
    }

    public Age getAge() {
        return age;
    }

    public void setRange(Range range) {
        //only valid to have either age or age range, never both
        if (age != null) {
            age = null;
        }
        this.range = range;
    }

    public Range getRange() {
        return range;
    }

    public boolean isEmpty() {
        return (range == null && age == null);
    }

    public static AgeOrRange forAge(Age age)
    {
        AgeOrRange ageOrRange = new AgeOrRange();
        ageOrRange.setAge(age);
        return ageOrRange;
    }

    public static AgeOrRange forRange(Range range)
    {
        AgeOrRange ageOrRange = new AgeOrRange();
        ageOrRange.setRange(range);
        return ageOrRange;
    }
}
