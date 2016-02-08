package org.endeavourhealth.transform.common.valueSets;

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
