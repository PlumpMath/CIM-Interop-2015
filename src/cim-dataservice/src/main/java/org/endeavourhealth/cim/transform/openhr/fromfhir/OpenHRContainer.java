package org.endeavourhealth.cim.transform.openhr.fromfhir;

import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Event;

import java.util.ArrayList;
import java.util.List;

public class OpenHRContainer {
    private List<OpenHR001Event> events;

    public List<OpenHR001Event> getEvents() {
        if (events == null)
            events = new ArrayList<>();
        return events;
    }

    public void setEvents(List<OpenHR001Event> events) {
        this.events = events;
    }

}
