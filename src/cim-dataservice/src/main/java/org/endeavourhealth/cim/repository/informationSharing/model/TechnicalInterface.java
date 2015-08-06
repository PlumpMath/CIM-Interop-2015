package org.endeavourhealth.cim.repository.informationSharing.model;

public class TechnicalInterface {
	private Integer id;
	private String description;
	private Boolean enabled;
	private String messageType;
	private String messageFormat;
	private String messageFormatVersion;
	private String deliveryEndpoint;
	private String AckEndpoint;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageFormat() {
		return messageFormat;
	}
	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	public String getMessageFormatVersion() {
		return messageFormatVersion;
	}
	public void setMessageFormatVersion(String messageFormatVersion) {
		this.messageFormatVersion = messageFormatVersion;
	}

	public String getDeliveryEndpoint() {
		return deliveryEndpoint;
	}
	public void setDeliveryEndpoint(String deliveryEndpoint) {
		this.deliveryEndpoint = deliveryEndpoint;
	}

	public String getAckEndpoint() {
		return AckEndpoint;
	}
	public void setAckEndpoint(String ackEndpoint) {
		AckEndpoint = ackEndpoint;
	}
}
