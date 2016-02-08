
package org.endeavourhealth.transform.schemas.emisopen.eomgetpatientappointments;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppointmentStruct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppointmentStruct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SlotID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SiteID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SessionName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SessionDBID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HolderList" type="{http://www.e-mis.com/emisopen/MedicalRecord}HolderList" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppointmentStruct", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", propOrder = {
    "slotID",
    "siteID",
    "date",
    "startTime",
    "duration",
    "notes",
    "reason",
    "sessionName",
    "sessionDBID",
    "holderList",
    "status"
})
public class AppointmentStruct {

    @XmlElement(name = "SlotID", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
    protected int slotID;
    @XmlElement(name = "SiteID", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
    protected int siteID;
    @XmlElement(name = "Date", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", required = true)
    protected String date;
    @XmlElement(name = "StartTime", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", required = true)
    protected String startTime;
    @XmlElement(name = "Duration", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", required = true)
    protected String duration;
    @XmlElement(name = "Notes", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", required = true)
    protected String notes;
    @XmlElement(name = "Reason", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", required = true)
    protected String reason;
    @XmlElement(name = "SessionName", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", required = true)
    protected String sessionName;
    @XmlElement(name = "SessionDBID", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
    protected int sessionDBID;
    @XmlElement(name = "HolderList", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
    protected HolderList holderList;
    @XmlElement(name = "Status", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
    protected String status;

    /**
     * Gets the value of the slotID property.
     * 
     */
    public int getSlotID() {
        return slotID;
    }

    /**
     * Sets the value of the slotID property.
     * 
     */
    public void setSlotID(int value) {
        this.slotID = value;
    }

    /**
     * Gets the value of the siteID property.
     * 
     */
    public int getSiteID() {
        return siteID;
    }

    /**
     * Sets the value of the siteID property.
     * 
     */
    public void setSiteID(int value) {
        this.siteID = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the sessionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionName() {
        return sessionName;
    }

    /**
     * Sets the value of the sessionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionName(String value) {
        this.sessionName = value;
    }

    /**
     * Gets the value of the sessionDBID property.
     * 
     */
    public int getSessionDBID() {
        return sessionDBID;
    }

    /**
     * Sets the value of the sessionDBID property.
     * 
     */
    public void setSessionDBID(int value) {
        this.sessionDBID = value;
    }

    /**
     * Gets the value of the holderList property.
     * 
     * @return
     *     possible object is
     *     {@link HolderList }
     *     
     */
    public HolderList getHolderList() {
        return holderList;
    }

    /**
     * Sets the value of the holderList property.
     * 
     * @param value
     *     allowed object is
     *     {@link HolderList }
     *     
     */
    public void setHolderList(HolderList value) {
        this.holderList = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
