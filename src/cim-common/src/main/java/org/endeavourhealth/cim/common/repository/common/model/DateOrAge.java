package org.endeavourhealth.cim.common.repository.common.model;

public class DateOrAge {
    private PartialDateTime date;
    private Age age;

    public void setDate(PartialDateTime date) {
        //only valid to have either date or age set, never both
        if (age != null) {
            age = null;
        }
        this.date = date;
    }

    public PartialDateTime getDate() {
        return date;
    }

    public void setAge(Age age) {
        //only valid to have either date or age set, never both
        if (date != null) {
            date = null;
        }
        this.age = age;
    }

    public Age getAge() {
        return age;
    }

    public boolean isEmpty() {
        return (date == null && age == null);
    }

    public static DateOrAge forDate(PartialDateTime date)
    {
        DateOrAge dateOrAge = new DateOrAge();
        dateOrAge.setDate(date);
        return dateOrAge;
    }

    public static DateOrAge forAge(Age age)
    {
        DateOrAge dateOrAge = new DateOrAge();
        dateOrAge.setAge(age);
        return dateOrAge;
    }
}
