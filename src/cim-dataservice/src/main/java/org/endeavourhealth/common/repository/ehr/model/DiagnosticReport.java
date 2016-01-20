package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class DiagnosticReport {
	private UUID diagnosticReportId;
    private UUID patientId;
    private UUID serviceId;
    private Date dateEffective;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getDiagnosticReportId() {
		return diagnosticReportId;
	}
	public void setDiagnosticReportId(UUID diagnosticReportId) {
		this.diagnosticReportId = diagnosticReportId;
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
    public Date getDateEffective() {
        return dateEffective;
    }
    public void setDateEffective(Date dateEffective) {
        this.dateEffective = dateEffective;
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
