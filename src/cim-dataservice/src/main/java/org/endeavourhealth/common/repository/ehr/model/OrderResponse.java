package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class OrderResponse {
	private UUID orderResponseId;
    private UUID orderId;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getOrderResponseId() {
		return orderResponseId;
	}
	public void setOrderResponseId(UUID orderResponseId) {
		this.orderResponseId = orderResponseId;
	}
    public UUID getOrderId() {
        return orderId;
    }
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
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
