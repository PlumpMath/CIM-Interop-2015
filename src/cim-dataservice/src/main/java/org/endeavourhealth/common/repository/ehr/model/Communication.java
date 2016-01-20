package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Communication {
	private UUID communicationId;
    private UUID senderId;
    private Date effectiveDateTime;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getCommunicationId() {
		return communicationId;
	}
	public void setCommunicationId(UUID communicationId) {
		this.communicationId = communicationId;
	}
    public UUID getSenderId() {
        return senderId;
    }
    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }
    public Date getEffectiveDateTime() {
        return effectiveDateTime;
    }
    public void setEffectiveDateTime(Date effectiveDateTime) {
        this.effectiveDateTime = effectiveDateTime;
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
