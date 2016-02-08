package org.endeavourhealth.transform.common.valueSets;

public enum TaskPriorityCode {
	low,
	medium,
	high;

	public String value() {
		return name();
	}

	public static TaskPriorityCode fromValue(String v) {
		return valueOf(v);
	}

}
