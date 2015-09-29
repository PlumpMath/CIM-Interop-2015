package org.endeavourhealth.cim.transform.common.valueSets;

public enum TaskStatusCode {
	active,
	complete,
	deleted,
	archived;

	public String value() {
		return name();
	}

	public static TaskStatusCode fromValue(String v) {
		return valueOf(v);
	}

}
