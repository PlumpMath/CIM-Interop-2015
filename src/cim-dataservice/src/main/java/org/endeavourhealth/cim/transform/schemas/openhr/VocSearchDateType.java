
package org.endeavourhealth.cim.transform.schemas.openhr;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for voc.SearchDateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="voc.SearchDateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="BASELINE"/>
 *     &lt;enumeration value="REFERENCE"/>
 *     &lt;enumeration value="TODAY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "voc.SearchDateType", namespace = "http://www.e-mis.com/emisopen")
@XmlEnum
public enum VocSearchDateType {


    /**
     * Baseline date
     * 
     */
    BASELINE,

    /**
     * Reference date
     * 
     */
    REFERENCE,

    /**
     * Todays date
     * 
     */
    TODAY;

    public String value() {
        return name();
    }

    public static VocSearchDateType fromValue(String v) {
        return valueOf(v);
    }

}
