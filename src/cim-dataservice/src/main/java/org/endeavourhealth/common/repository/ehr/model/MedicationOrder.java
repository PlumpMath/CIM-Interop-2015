package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class MedicationOrder {
	private UUID medicationOrderId;
    private UUID patientId;
    private UUID serviceId;
    private Date effectiveDateTime;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getMedicationOrderId() {
		return medicationOrderId;
	}
	public void setMedicationOrderId(UUID medicationOrderId) {
		this.medicationOrderId = medicationOrderId;
	}
    public UUID getPatientId() {
        return patientId;
    }
    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }
    public UUID getServiceId() {
        return serviceId;
    }
    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
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
