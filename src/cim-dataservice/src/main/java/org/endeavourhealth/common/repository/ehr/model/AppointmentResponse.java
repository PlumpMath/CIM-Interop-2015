package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class AppointmentResponse {
	private UUID appointmentResponseId;
    private UUID appointmentId;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getAppointmentResponseId() {
		return appointmentResponseId;
	}
	public void setAppointmentResponseId(UUID appointmentResponseId) {
		this.appointmentResponseId = appointmentResponseId;
	}
    public UUID getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }
	public Map<String, String> getMetaData() {
		return metaData;
	}
    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }
    public String getEntryData() {
        return entryData;
    }
    public void setEntryData(String entryData) {
        this.entryData = entryData;
    }


    public Date getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }




}
