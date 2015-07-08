package org.endeavourhealth.cim.dataprotocols.protocol.models;

import org.endeavourhealth.cim.common.models.Schedule;

public class Endpoint {
    public enum TransportProtocol {
        WEBSERVICE,
        FTP
    }

    private TransportProtocol protocol;
    private String uri;
    private Schedule schedule;

    public TransportProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(TransportProtocol protocol) {
        this.protocol = protocol;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
