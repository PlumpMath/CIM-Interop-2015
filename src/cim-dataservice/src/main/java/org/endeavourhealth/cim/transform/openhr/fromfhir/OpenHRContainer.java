package org.endeavourhealth.cim.transform.openhr.fromfhir;

import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Event;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Problem;

import java.util.ArrayList;
import java.util.List;

public class OpenHRContainer {
    private List<OpenHR001Event> events;
    private List<OpenHR001Problem> problems;

    public List<OpenHR001Event> getEvents() {
        if (events == null)
            events = new ArrayList<>();
        return events;
    }

    public void setEvents(List<OpenHR001Event> events) {
        this.events = events;
    }

    public List<OpenHR001Problem> getProblems() {
        if (problems == null)
            problems = new ArrayList<>();
        return problems;
    }

    public void setProblems(List<OpenHR001Problem> problems) {
        this.problems = problems;
    }
}
