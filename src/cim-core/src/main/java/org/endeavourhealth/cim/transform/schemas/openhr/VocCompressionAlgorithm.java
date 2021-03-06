
package org.endeavourhealth.cim.transform.schemas.openhr;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for voc.CompressionAlgorithm.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="voc.CompressionAlgorithm">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="DF"/>
 *     &lt;enumeration value="GZ"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "voc.CompressionAlgorithm", namespace = "http://www.e-mis.com/emisopen")
@XmlEnum
public enum VocCompressionAlgorithm {


    /**
     * Deflate
     * 
     */
    DF,

    /**
     * Zip
     * 
     */
    GZ;

    public String value() {
        return name();
    }

    public static VocCompressionAlgorithm fromValue(String v) {
        return valueOf(v);
    }

}
