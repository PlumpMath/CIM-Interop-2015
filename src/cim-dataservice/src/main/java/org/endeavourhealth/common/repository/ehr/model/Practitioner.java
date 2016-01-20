package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Practitioner {
	private UUID practitionerId;
    private UUID serviceId;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getPractitionerId() {
		return practitionerId;
	}
	public void setPractitionerId(UUID practitionerId) {
		this.practitionerId = practitionerId;
	}
    public UUID getServiceId() {
        return serviceId;
    }
    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
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
