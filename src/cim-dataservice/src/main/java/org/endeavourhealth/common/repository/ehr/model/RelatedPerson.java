package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class RelatedPerson {
	private UUID relatedPersonId;
    private UUID patientId;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getRelatedPersonId() {
		return relatedPersonId;
	}
	public void setRelatedPersonId(UUID relatedPersonId) {
		this.relatedPersonId = relatedPersonId;
	}
    public UUID getPatientId() {
        return patientId;
    }
    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
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
