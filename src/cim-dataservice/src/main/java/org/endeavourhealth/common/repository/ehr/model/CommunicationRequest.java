package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class CommunicationRequest {
	private UUID communicationRequestId;
    private UUID senderId;
    private Date dateRequested;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getCommunicationRequestId() {
		return communicationRequestId;
	}
	public void setCommunicationRequestId(UUID communicationRequestId) {
		this.communicationRequestId = communicationRequestId;
	}
    public UUID getSenderId() {
        return senderId;
    }
    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }
    public Date getDateRequested() {
        return dateRequested;
    }
    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
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
