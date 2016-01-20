package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class ProcessResponse {
	private UUID processResponseId;
    private UUID processRequestId;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getProcessResponseId() {
		return processResponseId;
	}
	public void setProcessResponseId(UUID processResponseId) {
		this.processResponseId = processResponseId;
	}
    public UUID getProcessRequestId() {
        return processRequestId;
    }
    public void setProcessRequestId(UUID processRequestId) {
        this.processRequestId = processRequestId;
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
