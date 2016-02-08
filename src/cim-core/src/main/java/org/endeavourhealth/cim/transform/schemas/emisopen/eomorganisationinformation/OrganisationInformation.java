
package org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrganisationList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Organisation" type="{http://www.e-mis.com/emisopen/MedicalRecord}LocationType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="UserList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="User" type="{http://www.e-mis.com/emisopen/MedicalRecord}PersonType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ExternalOrganisationList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Organisation" type="{http://www.e-mis.com/emisopen/MedicalRecord}LocationType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ExternalPersonList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Person" type="{http://www.e-mis.com/emisopen/MedicalRecord}PersonType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="LocationTypeList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="LocationType" type="{http://www.e-mis.com/emisopen/MedicalRecord}TypeOfLocationType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "organisationList",
    "userList",
    "externalOrganisationList",
    "externalPersonList",
    "locationTypeList"
})
@XmlRootElement(name = "OrganisationInformation", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
public class OrganisationInformation {

    @XmlElement(name = "OrganisationList", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", required = true)
    protected OrganisationInformation.OrganisationList organisationList;
    @XmlElement(name = "UserList", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", required = true)
    protected OrganisationInformation.UserList userList;
    @XmlElement(name = "ExternalOrganisationList", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
    protected OrganisationInformation.ExternalOrganisationList externalOrganisationList;
    @XmlElement(name = "ExternalPersonList", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
    protected OrganisationInformation.ExternalPersonList externalPersonList;
    @XmlElement(name = "LocationTypeList", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
    protected OrganisationInformation.LocationTypeList locationTypeList;

    /**
     * Gets the value of the organisationList property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationInformation.OrganisationList }
     *     
     */
    public OrganisationInformation.OrganisationList getOrganisationList() {
        return organisationList;
    }

    /**
     * Sets the value of the organisationList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationInformation.OrganisationList }
     *     
     */
    public void setOrganisationList(OrganisationInformation.OrganisationList value) {
        this.organisationList = value;
    }

    /**
     * Gets the value of the userList property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationInformation.UserList }
     *     
     */
    public OrganisationInformation.UserList getUserList() {
        return userList;
    }

    /**
     * Sets the value of the userList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationInformation.UserList }
     *     
     */
    public void setUserList(OrganisationInformation.UserList value) {
        this.userList = value;
    }

    /**
     * Gets the value of the externalOrganisationList property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationInformation.ExternalOrganisationList }
     *     
     */
    public OrganisationInformation.ExternalOrganisationList getExternalOrganisationList() {
        return externalOrganisationList;
    }

    /**
     * Sets the value of the externalOrganisationList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationInformation.ExternalOrganisationList }
     *     
     */
    public void setExternalOrganisationList(OrganisationInformation.ExternalOrganisationList value) {
        this.externalOrganisationList = value;
    }

    /**
     * Gets the value of the externalPersonList property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationInformation.ExternalPersonList }
     *     
     */
    public OrganisationInformation.ExternalPersonList getExternalPersonList() {
        return externalPersonList;
    }

    /**
     * Sets the value of the externalPersonList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationInformation.ExternalPersonList }
     *     
     */
    public void setExternalPersonList(OrganisationInformation.ExternalPersonList value) {
        this.externalPersonList = value;
    }

    /**
     * Gets the value of the locationTypeList property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationInformation.LocationTypeList }
     *     
     */
    public OrganisationInformation.LocationTypeList getLocationTypeList() {
        return locationTypeList;
    }

    /**
     * Sets the value of the locationTypeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationInformation.LocationTypeList }
     *     
     */
    public void setLocationTypeList(OrganisationInformation.LocationTypeList value) {
        this.locationTypeList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Organisation" type="{http://www.e-mis.com/emisopen/MedicalRecord}LocationType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "organisation"
    })
    public static class ExternalOrganisationList {

        @XmlElement(name = "Organisation", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
        protected List<LocationType> organisation;

        /**
         * Gets the value of the organisation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the organisation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOrganisation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LocationType }
         * 
         * 
         */
        public List<LocationType> getOrganisation() {
            if (organisation == null) {
                organisation = new ArrayList<LocationType>();
            }
            return this.organisation;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Person" type="{http://www.e-mis.com/emisopen/MedicalRecord}PersonType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "person"
    })
    public static class ExternalPersonList {

        @XmlElement(name = "Person", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
        protected List<PersonType> person;

        /**
         * Gets the value of the person property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the person property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPerson().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PersonType }
         * 
         * 
         */
        public List<PersonType> getPerson() {
            if (person == null) {
                person = new ArrayList<PersonType>();
            }
            return this.person;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="LocationType" type="{http://www.e-mis.com/emisopen/MedicalRecord}TypeOfLocationType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "locationType"
    })
    public static class LocationTypeList {

        @XmlElement(name = "LocationType", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
        protected List<TypeOfLocationType> locationType;

        /**
         * Gets the value of the locationType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the locationType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLocationType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TypeOfLocationType }
         * 
         * 
         */
        public List<TypeOfLocationType> getLocationType() {
            if (locationType == null) {
                locationType = new ArrayList<TypeOfLocationType>();
            }
            return this.locationType;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Organisation" type="{http://www.e-mis.com/emisopen/MedicalRecord}LocationType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "organisation"
    })
    public static class OrganisationList {

        @XmlElement(name = "Organisation", namespace = "http://www.e-mis.com/emisopen/MedicalRecord", required = true)
        protected List<LocationType> organisation;

        /**
         * Gets the value of the organisation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the organisation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOrganisation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LocationType }
         * 
         * 
         */
        public List<LocationType> getOrganisation() {
            if (organisation == null) {
                organisation = new ArrayList<LocationType>();
            }
            return this.organisation;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="User" type="{http://www.e-mis.com/emisopen/MedicalRecord}PersonType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "user"
    })
    public static class UserList {

        @XmlElement(name = "User", namespace = "http://www.e-mis.com/emisopen/MedicalRecord")
        protected List<PersonType> user;

        /**
         * Gets the value of the user property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the user property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUser().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PersonType }
         * 
         * 
         */
        public List<PersonType> getUser() {
            if (user == null) {
                user = new ArrayList<PersonType>();
            }
            return this.user;
        }

    }

}
