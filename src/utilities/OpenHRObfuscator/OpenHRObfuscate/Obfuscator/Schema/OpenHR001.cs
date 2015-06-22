namespace OpenHR 
{
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.OpenHealthRecord", Namespace="http://www.e-mis.com/emisopen")]
    [System.Xml.Serialization.XmlRootAttribute("openHealthRecord", Namespace="http://www.e-mis.com/emisopen", IsNullable=false)]
    public partial class OpenHR001OpenHealthRecord {
        
        public System.Guid id;
        
        public System.DateTime creationTime;
        
        public OpenHR001MessageAuthor author;
        
        public OpenHR001ContentSpecification contentSpecification;
        
        public System.Guid requestMessage;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool requestMessageSpecified;
        
        public OpenHR001AdminDomain adminDomain;
        
        public OpenHR001HealthDomain healthDomain;
        
        [System.Xml.Serialization.XmlAttributeAttribute(DataType="token")]
        [System.ComponentModel.DefaultValueAttribute("1.3.0")]
        public string schemaVersion;
        
        public OpenHR001OpenHealthRecord() {
            this.schemaVersion = "1.3.0";
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.MessageAuthor", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001MessageAuthor {
        
        public OpenHR001AuthorSystem system;
        
        public System.Guid organisation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool organisationSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="token")]
        public string SDSIdentifier;
        
        public string nationalCode;
        
        public string displayName;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.AuthorSystem", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001AuthorSystem {
        
        public vocMessageAuthorSystemType systemType;
        
        public string displayName;
        
        public string version;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.MessageAuthorSystemType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocMessageAuthorSystemType {
        
        LV,
        
        GV,
        
        PCS,
        
        EMISWEB,
        
        EXT,
        
        GP2GP,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.ContentSpecification", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ContentSpecification {
        
        public vocContentSpecification specification;
        
        public dtTimeRange effectiveTime;
        
        public vocDataSet dataSet;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dataSetSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ContentSpecification", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocContentSpecification {
        
        [System.Xml.Serialization.XmlEnumAttribute("37241000000102")]
        Item37241000000102,
        
        [System.Xml.Serialization.XmlEnumAttribute("37231000000106")]
        Item37231000000106,
        
        [System.Xml.Serialization.XmlEnumAttribute("25881000000104")]
        Item25881000000104,
        
        [System.Xml.Serialization.XmlEnumAttribute("24791000000109")]
        Item24791000000109,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.TimeRange", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtTimeRange {
        
        [System.Xml.Serialization.XmlElementAttribute("baseHigh", typeof(dtTimeRangeBaseHigh))]
        [System.Xml.Serialization.XmlElementAttribute("baseLow", typeof(dtTimeRangeBaseLow))]
        public object Item;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtTimeRangeBaseHigh {
        
        public dtDatePart high;
        
        [System.Xml.Serialization.XmlElementAttribute("low", typeof(dtDatePart))]
        [System.Xml.Serialization.XmlElementAttribute("width", typeof(dtDuration))]
        public object Item;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.DatePart", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtDatePart {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocDatePart datepart;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public System.DateTime value;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool valueSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.DatePart", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocDatePart {
        
        U,
        
        Y,
        
        YM,
        
        YMD,
        
        YMDT,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.Duration", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtDuration {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocTimeUnit unit;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public int value;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.TimeUnit", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocTimeUnit {
        
        Y,
        
        M,
        
        W,
        
        D,
        
        H,
        
        N,
        
        S,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtTimeRangeBaseLow {
        
        public dtDatePart low;
        
        [System.Xml.Serialization.XmlElementAttribute("high", typeof(dtDatePart))]
        [System.Xml.Serialization.XmlElementAttribute("width", typeof(dtDuration))]
        public object Item;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.DataSet", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocDataSet {
        
        RSC,
        
        CIC,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.AdminDomain", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001AdminDomain {
        
        [System.Xml.Serialization.XmlElementAttribute("person")]
        public OpenHR001Person[] person;
        
        [System.Xml.Serialization.XmlElementAttribute("patient")]
        public OpenHR001Patient[] patient;
        
        [System.Xml.Serialization.XmlElementAttribute("organisation")]
        public OpenHR001Organisation[] organisation;
        
        [System.Xml.Serialization.XmlElementAttribute("location")]
        public OpenHR001Location[] location;
        
        [System.Xml.Serialization.XmlElementAttribute("user")]
        public OpenHR001User[] user;
        
        [System.Xml.Serialization.XmlElementAttribute("role")]
        public OpenHR001Role[] role;
        
        [System.Xml.Serialization.XmlElementAttribute("userInRole")]
        public OpenHR001UserInRole[] userInRole;
        
        [System.Xml.Serialization.XmlElementAttribute("localMixture")]
        public OpenHR001LocalMixture[] localMixture;
        
        [System.Xml.Serialization.XmlElementAttribute("auditTrail")]
        public OpenHR001AuditTrail[] auditTrail;
        
        [System.Xml.Serialization.XmlElementAttribute("confidentialityPolicy")]
        public OpenHR001ConfidentialityPolicy[] confidentialityPolicy;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Person", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Person : dtdbo {
        
        public System.Guid id;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime birthDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool birthDateSpecified;
        
        public vocSex sex;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool sexSpecified;
        
        public string forenames;
        
        public string callingName;
        
        public string surname;
        
        public string birthSurname;
        
        public string previousSurname;
        
        public string title;
        
        [System.Xml.Serialization.XmlElementAttribute("address")]
        public OpenHR001PersonAddress[] address;
        
        [System.Xml.Serialization.XmlElementAttribute("contact")]
        public dtContact[] contact;
        
        public dtCode maritalStatus;
        
        public dtCode ethnicGroup;
        
        public dtCode religion;
        
        public dtCode nationality;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.Sex", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocSex {
        
        U,
        
        M,
        
        F,
        
        I,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001PersonAddress : dtAddress {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(vocUpdateMode.none)]
        public vocUpdateMode updateMode;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public System.DateTime auditDeleteDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool auditDeleteDateSpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public System.Guid auditDeleteUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool auditDeleteUserInRoleSpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string auditDeleteInfo;
        
        public OpenHR001PersonAddress() {
            this.updateMode = vocUpdateMode.none;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.UpdateMode", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocUpdateMode {
        
        add,
        
        edit,
        
        delete,
        
        none,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.Address", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtAddress {
        
        public System.Guid id;
        
        public vocAddressType addressType;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool addressTypeSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="token")]
        public string addressKey;
        
        public dtPostcodeData postcodeData;
        
        public string postCode;
        
        public string houseNameFlat;
        
        public string street;
        
        public string village;
        
        public string town;
        
        public string county;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AddressType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAddressType {
        
        H,
        
        W,
        
        TMP,
        
        CAR,
        
        SCH,
        
        L,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.PostcodeData", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtPostcodeData {
        
        public string CountryCode;
        
        public string PCTCode;
        
        public decimal TownsendScore;
        
        public decimal IMD;
        
        public decimal HouseInPoorCondition;
        
        public decimal CombinedAirQualityIndex;
        
        public decimal NitrogenDioxide;
        
        public decimal PM10;
        
        public decimal SulphurDioxide;
        
        public decimal Benzene;
        
        public string RuralityEnglandWales;
        
        public string RuralityScotland;
        
        public int Version;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.Contact", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtContact : dtdbo {
        
        public System.Guid id;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool idSpecified;
        
        public vocContactType contactType;
        
        public string value;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ContactType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocContactType {
        
        H,
        
        W,
        
        M,
        
        FX,
        
        EM,
        
        VC,
        
        EMS,
    }
    
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001EtpPrescription))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001PatientTask))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001AuditTrail))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001ConfidentialityPolicy))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Attachment))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001DocumentFolder))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Document))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001EpisodeProblemLink))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001ProblemEventLink))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001ProblemHierarchy))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Problem))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001LocalMixtureConstituent))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001LocalMixture))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001OrderBatch))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Specimen))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001AuthorisedIssue))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001DiaryReminder))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Event))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Component))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Encounter))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001User))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Role))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(dtUserIdentifier))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001UserInRole))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Location))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Caseload))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Organisation))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001PatientCarer))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001CustomRegistration))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001CaseloadPatientProperty))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001CaseloadPatient))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(dtPatientIdentifier))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Patient))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(dtContact))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001Person))]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.dbo", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtdbo {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(vocUpdateMode.none)]
        public vocUpdateMode updateMode;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public System.DateTime auditDeleteDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool auditDeleteDateSpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public System.Guid auditDeleteUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool auditDeleteUserInRoleSpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string auditDeleteInfo;
        
        public dtdbo() {
            this.updateMode = vocUpdateMode.none;
        }
    }
    
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(dtCodeQualified))]
    [System.Xml.Serialization.XmlIncludeAttribute(typeof(OpenHR001LocationType))]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.Code", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtCode {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocCodeSystem codeSystem;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string displayName;
        
        [System.Xml.Serialization.XmlAttributeAttribute(DataType="token")]
        public string code;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.CodeSystem", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocCodeSystem {
        
        [System.Xml.Serialization.XmlEnumAttribute("2.16.840.1.113883.2.1.6.2")]
        Item21684011138832162,
        
        [System.Xml.Serialization.XmlEnumAttribute("2.16.840.1.113883.2.1.6.1")]
        Item21684011138832161,
        
        [System.Xml.Serialization.XmlEnumAttribute("2.16.840.1.113883.2.1.3.2.4.15")]
        Item21684011138832132415,
        
        [System.Xml.Serialization.XmlEnumAttribute("2.16.840.1.113883.2.1.6.3")]
        Item21684011138832163,
        
        EMISDRUGGROUP,
        
        EMISAPPROVEDNAME,
        
        EMISPREPARATION,
        
        EMISCONSTITUENT,
        
        EMISAPPROVEDNAMEANDCONSTITUENT,
        
        EMISDRUGFORM,
        
        EMISDRUGGROUPALLERGY,
        
        EMISAPPROVEDNAMEALLERGY,
        
        EMISPREPARATIONALLERGY,
        
        EMISCONSTITUENTALLERGY,
        
        EMISAPPROVEDNAMEANDCONSTITUENTALLERGY,
        
        EMISDRUGFORMALLERGY,
        
        EMISINTERNAL,
        
        MULTILEX,
        
        UNKNOWN,
        
        EMISLOCAL,
        
        OLTR,
        
        EMISCODEID,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Patient", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Patient : dtdbo {
        
        public System.Guid id;
        
        [System.Xml.Serialization.XmlElementAttribute("patientIdentifier")]
        public dtPatientIdentifier[] patientIdentifier;
        
        public System.Guid patientPerson;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool patientPersonSpecified;
        
        public System.Guid registeredGPUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool registeredGPUserInRoleSpecified;
        
        public string previousGPName;
        
        public System.Guid usualGPUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool usualGPUserInRoleSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime dateOfEntryCountry;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dateOfEntryCountrySpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime dateOfEntryArea;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dateOfEntryAreaSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime dateOfDeath;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dateOfDeathSpecified;
        
        public bool dead;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool deadSpecified;
        
        public bool archived;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool archivedSpecified;
        
        public System.Guid confidentialityPolicy;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool confidentialityPolicySpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("caseloadPatient")]
        public OpenHR001CaseloadPatient[] caseloadPatient;
        
        [System.Xml.Serialization.XmlElementAttribute("caseloadPatientHistory")]
        public OpenHR001CaseloadPatient[] caseloadPatientHistory;
        
        [System.Xml.Serialization.XmlElementAttribute("consent")]
        public dtConsentValue[] consent;
        
        [System.Xml.Serialization.XmlElementAttribute("PatientCarerDetails")]
        public OpenHR001PatientCarer[] PatientCarerDetails;
        
        [System.Xml.Serialization.XmlElementAttribute("PatientContact")]
        public OpenHR001PatientContact[] PatientContact;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.PatientIdentifier", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtPatientIdentifier : dtdbo {
        
        public vocPatientIdentifierType identifierType;
        
        public string value;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.PatientIdentifierType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocPatientIdentifierType {
        
        NHS,
        
        ONHS,
        
        CHI,
        
        HC,
        
        GRO,
        
        UPCI,
        
        MOD,
        
        SSH,
        
        INS,
        
        ARMY,
        
        RAF,
        
        NAVY,
        
        HOSP,
        
        SSD,
        
        UPN,
        
        PREV,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.CaseloadPatient", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001CaseloadPatient : dtdbo {
        
        public System.Guid id;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool idSpecified;
        
        public System.Guid organisation;
        
        public System.DateTime dateRecorded;
        
        public System.DateTime dateEnded;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dateEndedSpecified;
        
        public OpenHR001PatientStatus patientStatus;
        
        public OpenHR001CaseloadPatientPatientType patientType;
        
        [System.Xml.Serialization.XmlElementAttribute("property")]
        public OpenHR001CaseloadPatientProperty[] property;
        
        [System.Xml.Serialization.XmlElementAttribute("customRegistrationField")]
        public OpenHR001CustomRegistration[] customRegistrationField;
        
        public int emisPatientNumber;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool emisPatientNumberSpecified;
        
        public System.Guid preferredHCPUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool preferredHCPUserInRoleSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.PatientStatus", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001PatientStatus {
        
        public vocPatientStatus status;
        
        public vocCaseloadPatientStatus caseloadStatus;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool caseloadStatusSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.PatientStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocPatientStatus {
        
        [System.Xml.Serialization.XmlEnumAttribute("1")]
        Item1,
        
        [System.Xml.Serialization.XmlEnumAttribute("2")]
        Item2,
        
        [System.Xml.Serialization.XmlEnumAttribute("3")]
        Item3,
        
        [System.Xml.Serialization.XmlEnumAttribute("4")]
        Item4,
        
        [System.Xml.Serialization.XmlEnumAttribute("5")]
        Item5,
        
        [System.Xml.Serialization.XmlEnumAttribute("6")]
        Item6,
        
        [System.Xml.Serialization.XmlEnumAttribute("7")]
        Item7,
        
        [System.Xml.Serialization.XmlEnumAttribute("8")]
        Item8,
        
        [System.Xml.Serialization.XmlEnumAttribute("9")]
        Item9,
        
        [System.Xml.Serialization.XmlEnumAttribute("10")]
        Item10,
        
        [System.Xml.Serialization.XmlEnumAttribute("11")]
        Item11,
        
        [System.Xml.Serialization.XmlEnumAttribute("12")]
        Item12,
        
        [System.Xml.Serialization.XmlEnumAttribute("13")]
        Item13,
        
        [System.Xml.Serialization.XmlEnumAttribute("14")]
        Item14,
        
        [System.Xml.Serialization.XmlEnumAttribute("15")]
        Item15,
        
        [System.Xml.Serialization.XmlEnumAttribute("16")]
        Item16,
        
        [System.Xml.Serialization.XmlEnumAttribute("17")]
        Item17,
        
        [System.Xml.Serialization.XmlEnumAttribute("18")]
        Item18,
        
        [System.Xml.Serialization.XmlEnumAttribute("19")]
        Item19,
        
        [System.Xml.Serialization.XmlEnumAttribute("20")]
        Item20,
        
        [System.Xml.Serialization.XmlEnumAttribute("21")]
        Item21,
        
        [System.Xml.Serialization.XmlEnumAttribute("22")]
        Item22,
        
        [System.Xml.Serialization.XmlEnumAttribute("23")]
        Item23,
        
        [System.Xml.Serialization.XmlEnumAttribute("24")]
        Item24,
        
        [System.Xml.Serialization.XmlEnumAttribute("25")]
        Item25,
        
        [System.Xml.Serialization.XmlEnumAttribute("26")]
        Item26,
        
        [System.Xml.Serialization.XmlEnumAttribute("27")]
        Item27,
        
        [System.Xml.Serialization.XmlEnumAttribute("28")]
        Item28,
        
        [System.Xml.Serialization.XmlEnumAttribute("29")]
        Item29,
        
        [System.Xml.Serialization.XmlEnumAttribute("30")]
        Item30,
        
        [System.Xml.Serialization.XmlEnumAttribute("31")]
        Item31,
        
        [System.Xml.Serialization.XmlEnumAttribute("32")]
        Item32,
        
        [System.Xml.Serialization.XmlEnumAttribute("33")]
        Item33,
        
        [System.Xml.Serialization.XmlEnumAttribute("34")]
        Item34,
        
        [System.Xml.Serialization.XmlEnumAttribute("35")]
        Item35,
        
        [System.Xml.Serialization.XmlEnumAttribute("36")]
        Item36,
        
        [System.Xml.Serialization.XmlEnumAttribute("37")]
        Item37,
        
        [System.Xml.Serialization.XmlEnumAttribute("38")]
        Item38,
        
        [System.Xml.Serialization.XmlEnumAttribute("39")]
        Item39,
        
        [System.Xml.Serialization.XmlEnumAttribute("40")]
        Item40,
        
        [System.Xml.Serialization.XmlEnumAttribute("41")]
        Item41,
        
        [System.Xml.Serialization.XmlEnumAttribute("42")]
        Item42,
        
        [System.Xml.Serialization.XmlEnumAttribute("43")]
        Item43,
        
        [System.Xml.Serialization.XmlEnumAttribute("44")]
        Item44,
        
        [System.Xml.Serialization.XmlEnumAttribute("45")]
        Item45,
        
        [System.Xml.Serialization.XmlEnumAttribute("46")]
        Item46,
        
        [System.Xml.Serialization.XmlEnumAttribute("47")]
        Item47,
        
        [System.Xml.Serialization.XmlEnumAttribute("48")]
        Item48,
        
        [System.Xml.Serialization.XmlEnumAttribute("49")]
        Item49,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.CaseloadPatientStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocCaseloadPatientStatus {
        
        REG,
        
        LEFT,
        
        DEAD,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001CaseloadPatientPatientType {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public bool dummy;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dummySpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public bool userDefined;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool userDefinedSpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string originalTerm;
        
        [System.Xml.Serialization.XmlTextAttribute()]
        public vocPatientType Value;
        
        public OpenHR001CaseloadPatientPatientType() {
            this.dummy = false;
            this.userDefined = false;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.PatientType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocPatientType {
        
        E,
        
        IN,
        
        PRIV,
        
        REG,
        
        TMP,
        
        COMM,
        
        DUM,
        
        OTH,
        
        CON,
        
        MAT,
        
        CHS,
        
        WI,
        
        MIS,
        
        SEX,
        
        PRE,
        
        YEL,
        
        DER,
        
        DIA,
        
        RHM,
        
        CHR,
        
        CHC,
        
        ULT,
        
        BCG,
        
        VAS,
        
        ACU,
        
        REF,
        
        HYP,
        
        OOH,
        
        RBN,
        
        ANT,
        
        AUD,
        
        GYN,
        
        DOP,
        
        SEC,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.CaseloadPatientProperty", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001CaseloadPatientProperty : dtdbo {
        
        public vocCaseloadPatientProperty name;
        
        public string value;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.CaseloadPatientProperty", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocCaseloadPatientProperty {
        
        WQ,
        
        WM,
        
        RM,
        
        RDM,
        
        FM,
        
        BSM,
        
        TPHA,
        
        RIL,
        
        BS,
        
        RA,
        
        FZ,
        
        DISP,
        
        MSM,
        
        DQ,
        
        EED,
        
        RD,
        
        RS,
        
        RESS,
        
        AWN,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.CustomRegistration", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001CustomRegistration : dtdbo {
        
        public System.Guid id;
        
        public System.Guid template;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool templateSpecified;
        
        public string name;
        
        [System.Xml.Serialization.XmlElementAttribute("value")]
        public string[] value;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.ConsentValue", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtConsentValue {
        
        public string consentType;
        
        public bool consentValue;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.PatientCarer", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001PatientCarer : dtdbo {
        
        public string PersonGUID;
        
        public string ResponsiblePCT;
        
        public System.DateTime AuthorisationDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool AuthorisationDateSpecified;
        
        public string Relationship;
        
        public string Title;
        
        public string Forenames;
        
        public string Surname;
        
        public string Sex;
        
        public System.DateTime DateOfBirth;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool DateOfBirthSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("CarerAddress")]
        public dtAddress[] CarerAddress;
        
        public string Telephone1;
        
        public string Telephone2;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.PatientContact", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001PatientContact {
        
        public string ContactGUID;
        
        public int PatientAssociateTypeId;
        
        public string Title;
        
        public string Forenames;
        
        public string Surname;
        
        public string HomePhone;
        
        public string WorkPhone;
        
        public string MobilePhone;
        
        public bool NextOfKin;
        
        public bool EmergencyContact;
        
        public bool CanDiscussRecord;
        
        public string Notes;
        
        public string LinkedPatientId;
        
        [System.Xml.Serialization.XmlElementAttribute("PatientAssociateTypeUserSpecified")]
        public string PatientAssociateTypeUserSpecified1;
        
        [System.Xml.Serialization.XmlElementAttribute("ContactAddress")]
        public dtAddress[] ContactAddress;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Organisation", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Organisation : dtdbo {
        
        public System.Guid id;
        
        public string name;
        
        public dtCode organisationType;
        
        public OpenHR001OrganisationSpeciality speciality;
        
        public int cdb;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool cdbSpecified;
        
        public string nationalPracticeCode;
        
        public System.Guid mainLocation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool mainLocationSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("locations")]
        public OpenHR001OrganisationLocations[] locations;
        
        public OpenHR001Caseload caseload;
        
        public System.Guid parentOrganisation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool parentOrganisationSpecified;
        
        public System.DateTime openDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool openDateSpecified;
        
        public System.DateTime closeDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool closeDateSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001OrganisationSpeciality : dtCode {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public int nationalCode;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool nationalCodeSpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string specialityAbbreviation;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001OrganisationLocations : dtdbo {
        
        public System.Guid location;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Caseload", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Caseload : dtdbo {
        
        public System.Guid id;
        
        public string title;
        
        public bool referredOnly;
        
        public bool includeReferrals;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Location", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Location : dtdbo {
        
        public System.Guid id;
        
        public string name;
        
        public OpenHR001LocationType locationType;
        
        public System.Guid parentLocation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool parentLocationSpecified;
        
        public System.Guid mainContactPerson;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool mainContactPersonSpecified;
        
        public string notes;
        
        public System.DateTime openDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool openDateSpecified;
        
        public System.DateTime closeDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool closeDateSpecified;
        
        public dtAddress address;
        
        [System.Xml.Serialization.XmlElementAttribute("contact")]
        public dtContact[] contact;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.LocationType", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001LocationType : dtCode {
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.User", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001User : dtdbo {
        
        public System.Guid id;
        
        public System.Guid userPerson;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool userPersonSpecified;
        
        public string mnemonic;
        
        public string loginName;
        
        public bool active;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool activeSpecified;
        
        public System.Guid defaultUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool defaultUserInRoleSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary")]
        public byte[] password;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Role", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Role : dtdbo {
        
        public System.Guid id;
        
        public string name;
        
        public dtCode userCategory;
        
        public System.Guid organisation;
        
        public System.Guid confidentialityPolicy;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.UserInRole", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001UserInRole : dtdbo {
        
        public System.Guid id;
        
        public System.Guid user;
        
        public System.Guid role;
        
        public vocContractualRelationship contractualRelationship;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime contractStart;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime contractEnd;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool contractEndSpecified;
        
        public System.Guid filingConfidentialityPolicy;
        
        [System.Xml.Serialization.XmlElementAttribute("userIdentifier")]
        public dtUserIdentifier[] userIdentifier;
        
        public bool authoriseScripts;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool authoriseScriptsSpecified;
        
        public bool consulter;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool consulterSpecified;
        
        public bool sessionHolder;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool sessionHolderSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ContractualRelationship", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocContractualRelationship {
        
        E,
        
        A,
        
        P,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.UserIdentifier", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtUserIdentifier : dtdbo {
        
        public vocUserIdentifierType identifierType;
        
        public string value;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.UserIdentifierType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocUserIdentifierType {
        
        GP,
        
        NAT,
        
        PRES,
        
        UKCC,
        
        RSBP,
        
        GDC,
        
        REG,
        
        WP,
        
        GOC,
        
        Spurious,
        
        CypherNumber,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.LocalMixture", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001LocalMixture : dtdbo {
        
        public System.Guid id;
        
        public string name;
        
        public System.Guid organisation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool organisationSpecified;
        
        public bool deleted;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool deletedSpecified;
        
        public System.DateTime createdTime;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool createdTimeSpecified;
        
        public System.Guid creatingUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool creatingUserInRoleSpecified;
        
        public System.DateTime editedTime;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool editedTimeSpecified;
        
        public System.Guid editedUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool editedUserInRoleSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("constituent")]
        public OpenHR001LocalMixtureConstituent[] constituent;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.LocalMixtureConstituent", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001LocalMixtureConstituent : dtdbo {
        
        public dtCode preparation;
        
        public string strength;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.AuditTrail", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001AuditTrail : dtdbo {
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.ConfidentialityPolicy", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ConfidentialityPolicy : dtdbo {
        
        public System.Guid id;
        
        public string name;
        
        public vocConfidentialityPolicyNotificationType notificationType;
        
        public System.Guid ownerUser;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool ownerUserSpecified;
        
        public bool showHiddenRecordIndicator;
        
        public System.Guid organisation;
        
        public bool restrictToOrganisation;
        
        [System.Xml.Serialization.XmlElementAttribute("userCategory")]
        public dtCode[] userCategory;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ConfidentialityPolicyNotificationType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocConfidentialityPolicyNotificationType {
        
        NO,
        
        REPT,
        
        MESG,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.HealthDomain", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001HealthDomain {
        
        [System.Xml.Serialization.XmlElementAttribute("encounter")]
        public OpenHR001Encounter[] encounter;
        
        [System.Xml.Serialization.XmlElementAttribute("event")]
        public OpenHR001HealthDomainEvent[] @event;
        
        [System.Xml.Serialization.XmlElementAttribute("problem")]
        public OpenHR001Problem[] problem;
        
        [System.Xml.Serialization.XmlElementAttribute("document")]
        public OpenHR001Document[] document;
        
        [System.Xml.Serialization.XmlElementAttribute("task")]
        public OpenHR001PatientTask[] task;
        
        [System.Xml.Serialization.XmlElementAttribute("prescription")]
        public OpenHR001EtpPrescription[] prescription;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Encounter", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Encounter : dtdbo {
        
        public System.Guid id;
        
        public System.Guid patient;
        
        public dtDatePart effectiveTime;
        
        public System.DateTime availabilityTimeStamp;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool availabilityTimeStampSpecified;
        
        public System.Guid authorisingUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool authorisingUserInRoleSpecified;
        
        public System.Guid enteredByUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool enteredByUserInRoleSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("organisation")]
        public System.Guid[] organisation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool organisationSpecified;
        
        public System.Guid confidentialityPolicy;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool confidentialityPolicySpecified;
        
        public vocConsultationType consultationType;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool consultationTypeSpecified;
        
        public System.Guid location;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool locationSpecified;
        
        public OpenHR001LocationType locationType;
        
        [System.Xml.Serialization.XmlElementAttribute("accompanyingHCP")]
        public System.Guid[] accompanyingHCP;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool accompanyingHCPSpecified;
        
        public dtDuration duration;
        
        public dtDuration travelTime;
        
        public System.Guid appointmentEvent;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool appointmentEventSpecified;
        
        public OpenHR001TemplateIdentifier template;
        
        public string sectionData;
        
        [System.Xml.Serialization.XmlElementAttribute("component")]
        public OpenHR001Component[] component;
        
        public dtCodeQualified clinicalPurpose;
        
        public string externalConsulter;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(true)]
        public bool complete;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(false)]
        public bool removeComponentsOnDelete;
        
        public OpenHR001Encounter() {
            this.complete = true;
            this.removeComponentsOnDelete = false;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ConsultationType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocConsultationType {
        
        GP,
        
        ASS,
        
        INT,
        
        TFC,
        
        TTC,
        
        TFP,
        
        TTP,
        
        TFO,
        
        TTO,
        
        FFC,
        
        FFP,
        
        FFO,
        
        OC,
        
        HV,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.TemplateIdentifier", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001TemplateIdentifier {
        
        public System.Guid id;
        
        public System.Guid instance;
        
        public System.Guid inputPrompt;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool inputPromptSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Component", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Component : dtdbo {
        
        public System.Guid @event;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool eventSpecified;
        
        public byte problemPage;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool problemPageSpecified;
        
        public dtCode heading;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.CodeQualified", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtCodeQualified : dtCode {
        
        [System.Xml.Serialization.XmlElementAttribute("qualifier")]
        public dtQualifier[] qualifier;
        
        [System.Xml.Serialization.XmlElementAttribute("translation")]
        public dtCodeQualified[] translation;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.Qualifier", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtQualifier {
        
        public dtCode name;
        
        public dtCode value;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001HealthDomainEvent : OpenHR001Event {
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Event", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Event : dtdbo {
        
        public System.Guid id;
        
        public System.Guid patient;
        
        public vocEventType eventType;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool eventTypeSpecified;
        
        public dtDatePart effectiveTime;
        
        public System.DateTime availabilityTimeStamp;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool availabilityTimeStampSpecified;
        
        public System.Guid authorisingUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool authorisingUserInRoleSpecified;
        
        public System.Guid enteredByUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool enteredByUserInRoleSpecified;
        
        public dtCodeQualified code;
        
        public string displayTerm;
        
        [System.Xml.Serialization.XmlElementAttribute("associatedText")]
        public OpenHR001EventAssociatedText[] associatedText;
        
        [System.Xml.Serialization.XmlElementAttribute("organisation")]
        public System.Guid[] organisation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool organisationSpecified;
        
        public System.Guid confidentialityPolicy;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool confidentialityPolicySpecified;
        
        [System.ComponentModel.DefaultValueAttribute(false)]
        public bool hidden;
        
        public OpenHR001TemplateIdentifier template;
        
        [System.Xml.Serialization.XmlElementAttribute("alert", typeof(OpenHR001Alert))]
        [System.Xml.Serialization.XmlElementAttribute("allergy", typeof(OpenHR001Allergy))]
        [System.Xml.Serialization.XmlElementAttribute("diary", typeof(OpenHR001Diary))]
        [System.Xml.Serialization.XmlElementAttribute("medication", typeof(OpenHR001Medication))]
        [System.Xml.Serialization.XmlElementAttribute("medicationIssue", typeof(OpenHR001MedicationIssue))]
        [System.Xml.Serialization.XmlElementAttribute("observation", typeof(OpenHR001Observation))]
        [System.Xml.Serialization.XmlElementAttribute("orderHeader", typeof(OpenHR001OrderHeader))]
        [System.Xml.Serialization.XmlElementAttribute("referral", typeof(OpenHR001Referral))]
        [System.Xml.Serialization.XmlElementAttribute("report", typeof(OpenHR001Report))]
        public object Item;
        
        public OpenHR001Annotations annotations;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public bool unFiled;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool unFiledSpecified;
        
        public OpenHR001Event() {
            this.hidden = false;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.EventType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocEventType {
        
        OBS,
        
        MED,
        
        TR,
        
        INV,
        
        VAL,
        
        ISS,
        
        ATT,
        
        REF,
        
        DRY,
        
        ALT,
        
        ALL,
        
        FH,
        
        IMM,
        
        REP,
        
        OH,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001EventAssociatedText : OpenHR001AssociatedText {
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.AssociatedText", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001AssociatedText {
        
        public string value;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocAssociatedTextType associatedTextType;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AssociatedTextType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAssociatedTextType {
        
        RC,
        
        FC,
        
        PRE,
        
        POST,
        
        HEAD,
        
        UC,
        
        DI,
        
        CI,
        
        PT,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Alert", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Alert {
        
        public bool triggerBookSlot;
        
        public bool triggerAriveSwap;
        
        public bool showConsultation;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime expires;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool expiresSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Allergy", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Allergy {
        
        public dtCode targetDrug;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocObservationResultStatus resultStatus;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool resultStatusSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ObservationResultStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocObservationResultStatus {
        
        PR,
        
        IN,
        
        NA,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Diary", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Diary {
        
        public string durationTerm;
        
        public OpenHR001LocationType locationType;
        
        public bool isComplete;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool isCompleteSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("reminder")]
        public OpenHR001DiaryReminder[] reminder;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.DiaryReminder", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001DiaryReminder : dtdbo {
        
        public vocReminderType reminderType;
        
        public System.DateTime reminderTime;
        
        public System.Guid userInRole;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ReminderType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocReminderType {
        
        UNK,
        
        L,
        
        T,
        
        FX,
        
        EM,
        
        SMS,
        
        RHS,
        
        V,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Medication", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Medication {
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime reviewDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool reviewDateSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime expiryDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool expiryDateSpecified;
        
        public System.Guid localMixture;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool localMixtureSpecified;
        
        public string dosage;
        
        public decimal quantity;
        
        public string quantityUnit;
        
        public string quantityRepresentation;
        
        public int durationOfIssue;
        
        public vocPrescriptionType prescriptionType;
        
        public string pharmacyText;
        
        public string patientText;
        
        public vocDrugStatus drugStatus;
        
        public bool @private;
        
        public int minNextIssueDays;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool minNextIssueDaysSpecified;
        
        public int maxNextIssueDays;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool maxNextIssueDaysSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime firstIssueDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool firstIssueDateSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime mostRecentIssueDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool mostRecentIssueDateSpecified;
        
        public int numberOfIssues;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool numberOfIssuesSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("authorisedIssues")]
        public OpenHR001AuthorisedIssue[] authorisedIssues;
        
        public int averageCompliance;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool averageComplianceSpecified;
        
        public vocDrugIssueType mostRecentIssueMethod;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool mostRecentIssueMethodSpecified;
        
        public bool prescribedAsContraceptive;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool prescribedAsContraceptiveSpecified;
        
        public OpenHR001MedicationCancel cancellation;
        
        public long dmdUnitOfMeasure;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dmdUnitOfMeasureSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.PrescriptionType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocPrescriptionType {
        
        A,
        
        R,
        
        D,
        
        U,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.DrugStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocDrugStatus {
        
        A,
        
        C,
        
        N,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.AuthorisedIssue", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001AuthorisedIssue : dtdbo {
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime authorisedDate;
        
        public System.Guid authorisingUserInRole;
        
        public System.Guid enteredByUserInRole;
        
        public int authorisedIssues;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.DrugIssueType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocDrugIssueType {
        
        P,
        
        Q,
        
        H,
        
        O,
        
        D,
        
        B,
        
        S,
        
        A,
        
        E,
        
        OTC,
        
        OOOH,
        
        OH,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.MedicationCancel", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001MedicationCancel {
        
        public System.Guid id;
        
        public System.Guid cancelledByUserInRole;
        
        public System.DateTime cancellationDateTime;
        
        public string cancellationText;
        
        public System.Guid linkedEvent;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool linkedEventSpecified;
        
        public vocDrugIssueCancellationType cancellationReason;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool cancellationReasonSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.DrugIssueCancellationType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocDrugIssueCancellationType {
        
        ERR,
        
        CON,
        
        CHG,
        
        CLN,
        
        REQ,
        
        PHARM,
        
        FAIL,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.MedicationIssue", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001MedicationIssue {
        
        public System.Guid medication;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool medicationSpecified;
        
        public System.Guid localMixture;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool localMixtureSpecified;
        
        public string dosage;
        
        public decimal quantity;
        
        public string quantityUnit;
        
        public string quantityRepresentation;
        
        public int durationOfIssue;
        
        public short compliance;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool complianceSpecified;
        
        public vocPrescriptionType prescriptionType;
        
        public decimal estimatedNHSCost;
        
        public string pharmacyStamp;
        
        public vocDrugIssueType issueMethod;
        
        public string pharmacyText;
        
        public string patientText;
        
        public string pharmacyMessage;
        
        public string patientMessage;
        
        public bool @private;
        
        public bool cancelled;
        
        public OpenHR001MedicationCancel cancellation;
        
        public bool prescribedAsContraceptive;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool prescribedAsContraceptiveSpecified;
        
        public long dmdUnitOfMeasure;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dmdUnitOfMeasureSpecified;
        
        public int repeatDispensingInterval;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool repeatDispensingIntervalSpecified;
        
        public int repeatDispensingIssueCount;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool repeatDispensingIssueCountSpecified;
        
        public string batchNumber;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Observation", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Observation {
        
        public OpenHR001ObservationValue value;
        
        public OpenHR001Abnormal abnormal;
        
        public OpenHR001ComplexObservation complexObservation;
        
        public vocEpisodicity episodicity;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool episodicitySpecified;
        
        public System.Guid referenceDocument;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool referenceDocumentSpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocObservationResultStatus resultStatus;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool resultStatusSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.ObservationValue", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ObservationValue {
        
        [System.Xml.Serialization.XmlElementAttribute("availableRange")]
        public OpenHR001Range[] availableRange;
        
        [System.Xml.Serialization.XmlElementAttribute("numeric", typeof(OpenHR001NumericValue))]
        [System.Xml.Serialization.XmlElementAttribute("text", typeof(OpenHR001TextValue))]
        public object Item;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Range", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Range {
        
        [System.Xml.Serialization.XmlElementAttribute("description", typeof(string))]
        [System.Xml.Serialization.XmlElementAttribute("numericRange", typeof(OpenHR001NumericRange))]
        [System.Xml.Serialization.XmlElementAttribute("textRange", typeof(OpenHR001TextRange))]
        public object Item;
        
        public string units;
        
        public vocSex sex;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool sexSpecified;
        
        public dtAgeRange ageRange;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocRangeQualifier qualifier;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool qualifierSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.NumericRange", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001NumericRange {
        
        public OpenHR001RangeValue low;
        
        public OpenHR001RangeValue high;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.RangeValue", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001RangeValue {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public decimal value;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string @operator;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.TextRange", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001TextRange {
        
        public string low;
        
        public string high;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.AgeRange", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtAgeRange {
        
        public dtAge low;
        
        public dtAge high;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.Age", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtAge {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocAgeUnit unit;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public short value;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AgeUnit", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAgeUnit {
        
        Y,
        
        M,
        
        W,
        
        D,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.RangeQualifier", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocRangeQualifier {
        
        U,
        
        T,
        
        X,
        
        R,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.NumericValue", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001NumericValue {
        
        public string @operator;
        
        public decimal value;
        
        public string units;
        
        public string ucumUnits;
        
        public OpenHR001Range range;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.TextValue", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001TextValue {
        
        public string value;
        
        public OpenHR001Range range;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Abnormal", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Abnormal {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public bool value;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string description;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.ComplexObservation", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ComplexObservation {
        
        public System.Guid parentLink;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool parentLinkSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("childLink")]
        public System.Guid[] childLink;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool childLinkSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.Episodicity", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocEpisodicity {
        
        NONE,
        
        FIRST,
        
        NEW,
        
        REV,
        
        FLA,
        
        END,
        
        CHG,
        
        EVO,
        
        OUT,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.OrderHeader", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001OrderHeader {
        
        public bool Urgent;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool UrgentSpecified;
        
        public bool inoculationRisk;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool inoculationRiskSpecified;
        
        public bool fasted;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool fastedSpecified;
        
        public string clinicalDetails;
        
        public System.Guid sampleWorkflowTaskId;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool sampleWorkflowTaskIdSpecified;
        
        public System.Guid sampledByUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool sampledByUserInRoleSpecified;
        
        public vocRequestMode requestMode;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool requestModeSpecified;
        
        public System.Guid onlineTrackerGUID;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool onlineTrackerGUIDSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("orderBatch")]
        public OpenHR001OrderBatch[] orderBatch;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.RequestMode", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocRequestMode {
        
        ONLINE,
        
        OFFLINE,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.OrderBatch", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001OrderBatch : dtdbo {
        
        public System.Guid observationGUID;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool observationGUIDSpecified;
        
        public string orderReference;
        
        public vocOrderBatchStatus status;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool statusSpecified;
        
        public System.DateTime testStatusDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool testStatusDateSpecified;
        
        public System.Guid inboundReportGUID;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool inboundReportGUIDSpecified;
        
        public string labDisplayTerm;
        
        public dtCode bottleCode;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.OrderBatchStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocOrderBatchStatus {
        
        AWAITING_SAMPLE,
        
        AWAITING_RESULT,
        
        REQUEST_COMPLETE,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Referral", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Referral {
        
        public OpenHR001ReferralSpeciality speciality;
        
        public System.Guid targetUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool targetUserInRoleSpecified;
        
        public System.Guid targetOrganisation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool targetOrganisationSpecified;
        
        public vocReferralRequestType requestType;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool requestTypeSpecified;
        
        public string reason;
        
        public bool community;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool communitySpecified;
        
        public vocReferralUrgency urgency;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool urgencySpecified;
        
        public bool NHS;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool NHSSpecified;
        
        public vocReferralTransport transport;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool transportSpecified;
        
        public string referralRef;
        
        public vocReferralDirection direction;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool directionSpecified;
        
        public System.Guid sourceOrganisation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool sourceOrganisationSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime datedReferral;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool datedReferralSpecified;
        
        public string rejectedReason;
        
        public vocReferralMode referralMode;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool referralModeSpecified;
        
        public string UBRN;
        
        [System.Xml.Serialization.XmlElementAttribute("referralActivity")]
        public System.Guid[] referralActivity;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool referralActivitySpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ReferralSpeciality : dtCode {
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public int nationalCode;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool nationalCodeSpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string specialityAbbreviation;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ReferralRequestType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocReferralRequestType {
        
        INV,
        
        TRT,
        
        MA,
        
        PRC,
        
        PR,
        
        UNK,
        
        SELF,
        
        OUT,
        
        ADM,
        
        DAY,
        
        COM,
        
        DOM,
        
        ASS,
        
        ASED,
        
        NBN,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ReferralUrgency", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocReferralUrgency {
        
        R,
        
        S,
        
        U,
        
        D,
        
        W,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ReferralTransport", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocReferralTransport {
        
        N,
        
        R,
        
        S,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ReferralDirection", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocReferralDirection {
        
        NOTUSED,
        
        IN,
        
        OUT,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ReferralMode", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocReferralMode {
        
        E,
        
        T,
        
        V,
        
        W,
        
        M,
        
        C,
        
        A,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Report", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Report {
        
        public bool abnormal;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool abnormalSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime issueDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool issueDateSpecified;
        
        public System.DateTime receivedDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool receivedDateSpecified;
        
        public vocPathReportDataType dataType;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dataTypeSpecified;
        
        public string reportLabId;
        
        public string requestLabId;
        
        public System.DateTime requestDateTimeLab;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool requestDateTimeLabSpecified;
        
        public System.Guid serviceOrganisation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool serviceOrganisationSpecified;
        
        public System.Guid serviceLocation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool serviceLocationSpecified;
        
        public string servicePerson;
        
        public int messageId;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool messageIdSpecified;
        
        public OpenHR001MessageInterchange messageInterchange;
        
        public System.Guid orderHeader;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool orderHeaderSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("reportSummary")]
        public string[] reportSummary;
        
        [System.Xml.Serialization.XmlElementAttribute("viewerUserInRole")]
        public System.Guid[] viewerUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool viewerUserInRoleSpecified;
        
        public OpenHR001ReportPatientIdentifier patientIdentifier;
        
        [System.Xml.Serialization.XmlElementAttribute("component")]
        public OpenHR001ReportComponent[] component;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.PathReportDataType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocPathReportDataType {
        
        PATH,
        
        RAD,
        
        DIS,
        
        OUT,
        
        OOH,
        
        TRI,
        
        SCR,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.MessageInterchange", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001MessageInterchange {
        
        public System.Guid id;
        
        public string senderEDICode;
        
        public string receiverEDICode;
        
        public string interchange;
        
        public vocPathMessageType messageType;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.PathMessageType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocPathMessageType {
        
        ASTM,
        
        MEDRPT,
        
        NHS002,
        
        NHS003,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.ReportPatientIdentifier", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ReportPatientIdentifier {
        
        public OpenHR001ReportPatientIdentifierOriginalDetails originalDetails;
        
        public OpenHR001ReportPatientIdentifierMatchedDetails matchedDetails;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ReportPatientIdentifierOriginalDetails : OpenHR001ReportPatientIdentifierDetail {
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="token")]
        public string labSubjectId;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.ReportPatientIdentifierDetail", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ReportPatientIdentifierDetail {
        
        public string forenames;
        
        public string surname;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime birthDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool birthDateSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="token")]
        public string nhs;
        
        public dtAddress address;
        
        public vocSex sex;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool sexSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ReportPatientIdentifierMatchedDetails : OpenHR001ReportPatientIdentifierDetail {
        
        public System.Guid patient;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool patientSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ReportComponent {
        
        [System.Xml.Serialization.XmlElementAttribute("battery", typeof(System.Guid))]
        [System.Xml.Serialization.XmlElementAttribute("specimen", typeof(OpenHR001Specimen))]
        [System.Xml.Serialization.XmlElementAttribute("test", typeof(string), DataType="anyURI")]
        public object Item;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Specimen", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Specimen : dtdbo {
        
        public string labSpecimenReference;
        
        public string specimenType;
        
        public string fastingStatus;
        
        public decimal volume;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool volumeSpecified;
        
        public string volumeUnits;
        
        public string collectionProcedure;
        
        public string anatomicalOrigin;
        
        public System.DateTime sampleDateTime;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool sampleDateTimeSpecified;
        
        public System.DateTime collectionStartDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool collectionStartDateSpecified;
        
        public System.DateTime collectionEndDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool collectionEndDateSpecified;
        
        public System.DateTime dateTimeReceivedByLab;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dateTimeReceivedByLabSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("comment")]
        public string[] comment;
        
        [System.Xml.Serialization.XmlElementAttribute("battery")]
        public System.Guid[] battery;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool batterySpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("test")]
        public System.Guid[] test;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool testSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Annotations", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Annotations {
        
        public string annotation;
        
        public System.Guid imageGUID;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Problem", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Problem : dtdbo {
        
        public System.Guid id;
        
        public vocProblemStatus status;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool statusSpecified;
        
        public vocProblemSignificance significance;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool significanceSpecified;
        
        public dtDuration expectedDuration;
        
        public dtDatePart endTime;
        
        public vocProblemOwner owner;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool ownerSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("parentProblem")]
        public OpenHR001ProblemHierarchy[] parentProblem;
        
        [System.Xml.Serialization.XmlElementAttribute("childProblem")]
        public OpenHR001ProblemHierarchy[] childProblem;
        
        [System.Xml.Serialization.XmlElementAttribute("eventLink")]
        public OpenHR001ProblemEventLink[] eventLink;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ProblemStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocProblemStatus {
        
        A,
        
        I,
        
        HP,
        
        PP,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ProblemSignificance", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocProblemSignificance {
        
        S,
        
        M,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ProblemOwner", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocProblemOwner {
        
        IND,
        
        FAM,
        
        COM,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.ProblemHierarchy", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ProblemHierarchy : dtdbo {
        
        public System.Guid id;
        
        public vocProblemRelationshipType relationshipType;
        
        public System.Guid organisation;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ProblemRelationshipType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocProblemRelationshipType {
        
        COMB,
        
        GRP,
        
        REP,
        
        EVO,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.ProblemEventLink", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001ProblemEventLink : dtdbo {
        
        public System.Guid id;
        
        public vocEventType eventType;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool eventTypeSpecified;
        
        public vocProblemEventLinkType eventLinkType;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool eventLinkTypeSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ProblemEventLinkType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocProblemEventLinkType {
        
        ASS,
        
        FOL,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Document", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Document : dtdbo {
        
        public System.Guid id;
        
        public string name;
        
        public string description;
        
        public System.Guid uploadUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool uploadUserInRoleSpecified;
        
        public System.DateTime producedTime;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool producedTimeSpecified;
        
        public bool deleted;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool deletedSpecified;
        
        public OpenHR001DocumentFolder folder;
        
        [System.Xml.Serialization.XmlElementAttribute("observation")]
        public System.Guid[] observation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool observationSpecified;
        
        public vocAttachmentLocator locator;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool locatorSpecified;
        
        public dtDocumentData data;
        
        public dtCodeQualified code;
        
        public string annotationDefinition;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.DocumentFolder", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001DocumentFolder : dtdbo {
        
        public System.Guid id;
        
        public vocDocumentFolderType folderType;
        
        public string name;
        
        public bool deleted;
        
        public OpenHR001DocumentFolder() {
            this.deleted = false;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.DocumentFolderType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocDocumentFolderType {
        
        EPAT,
        
        EORG,
        
        EU,
        
        PAT,
        
        ORG,
        
        U,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AttachmentLocator", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAttachmentLocator {
        
        SF,
        
        URL,
        
        LOCAL,
        
        NOTAFILE,
        
        REFERENCE,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.DocumentData", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtDocumentData {
        
        [System.Xml.Serialization.XmlElementAttribute("documentData", typeof(string))]
        [System.Xml.Serialization.XmlElementAttribute("reference", typeof(string), DataType="anyURI")]
        [System.Xml.Serialization.XmlChoiceIdentifierAttribute("ItemElementName")]
        public string Item;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public ItemChoiceType ItemElementName;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocBinaryDataEncoding encoding;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool encodingSpecified;
        
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public vocCompressionAlgorithm compression;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool compressionSpecified;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace="http://www.e-mis.com/emisopen", IncludeInSchema=false)]
    public enum ItemChoiceType {
        
        documentData,
        
        reference,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.BinaryDataEncoding", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocBinaryDataEncoding {
        
        TXT,
        
        B64,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.CompressionAlgorithm", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocCompressionAlgorithm {
        
        DF,
        
        GZ,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.PatientTask", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001PatientTask : dtdbo {
        
        public System.Guid id;
        
        public System.Guid patient;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool patientSpecified;
        
        public vocTaskType taskType;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool taskTypeSpecified;
        
        public System.Guid creatingUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool creatingUserInRoleSpecified;
        
        [System.Xml.Serialization.XmlElementAttribute("owningUserInRole")]
        public System.Guid[] owningUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool owningUserInRoleSpecified;
        
        public vocTaskPriority priority;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool prioritySpecified;
        
        public vocTaskStatus status;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool statusSpecified;
        
        public System.DateTime createdDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool createdDateSpecified;
        
        public System.DateTime actionByDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool actionByDateSpecified;
        
        public System.DateTime expiryDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool expiryDateSpecified;
        
        public System.DateTime completionDate;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool completionDateSpecified;
        
        public string subject;
        
        public string description;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.TaskType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocTaskType {
        
        BOOK_APPOINTMENT,
        
        BOOK_APPOINTMENT_DOCTOR,
        
        BOOK_APPOINTMENT_NURSE,
        
        TELEPHONE_PATIENT,
        
        SCREEN_MESSAGE,
        
        RESULTS_FOR_INFO,
        
        MEETING_NOTIFICATION,
        
        PATIENT_NOTE,
        
        ADMIN_NOTE,
        
        FORM_TO_COMPLETE,
        
        REPEAT_TEST,
        
        ESCALATION_NOTIFICATION,
        
        CONFIDENTIALITY_POLICIES_OVERRIDDEN,
        
        DTS_TRANSMISSION_FAILURE,
        
        LEGITIMATE_RELATIONSHIP_NOTIFICATION,
        
        DTS_TRANSMISSION_REPORT,
        
        OVERDUE_TASK_NOTIFICATION,
        
        PRESCRIPTION,
        
        REFERRAL,
        
        UNARCHIVED_PATIENT_RECORD_NOTIFICATION,
        
        LEGITIMATE_RELATIONSHIP_NOTIFICATION_POPULATION,
        
        ORGANISATION_DEFINED_NON_PATIENT_TASK_TYPE,
        
        ORGANISATION_DEFINED_PATIENT_TASK_TYPE,
        
        REJECTION_NOTIFICATION,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.TaskPriority", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocTaskPriority {
        
        HIGH,
        
        LOW,
        
        NORMAL,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.TaskStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocTaskStatus {
        
        ACTIVE,
        
        COMPLETE,
        
        DELETED,
        
        ARCHIVED,
        
        REJECTED,
        
        DEFERRED,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.EtpPrescription", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001EtpPrescription : dtdbo {
        
        public System.Guid id;
        
        public vocEtpPrescriptionType type;
        
        public bool urgent;
        
        public vocEtpPrescriptionStatus status;
        
        public vocEtpPrescriptionRejectionCode rejectionCode;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool rejectionCodeSpecified;
        
        public string shortFormIdentifier;
        
        public System.Guid nominationOrganisation;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool nominationOrganisationSpecified;
        
        public System.Guid patient;
        
        public System.Guid enteredByUserInRole;
        
        public System.Guid stampDoctorUserInRole;
        
        public System.DateTime timeStamp;
        
        public string sdsRoleId;
        
        public string sdsRoleCode;
        
        public string sdsUserId;
        
        public string contactPhoneNumber;
        
        public string title;
        
        public string forenames;
        
        public string surname;
        
        public vocContractualRelationship contractualRelationship;
        
        [System.Xml.Serialization.XmlElementAttribute("issue")]
        public System.Guid[] issue;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool issueSpecified;
        
        public dtPrescriptionPostDated postDated;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.EtpPrescriptionType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocEtpPrescriptionType {
        
        NON_ELECTRONIC,
        
        EPSV1,
        
        EPSV2,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.EtpPrescriptionStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocEtpPrescriptionStatus {
        
        SEND_PENDING,
        
        SENT_TO_ETP,
        
        SEND_FAIL,
        
        REJECTED,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.EtpPrescriptionRejectionCode", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocEtpPrescriptionRejectionCode {
        
        [System.Xml.Serialization.XmlEnumAttribute("1")]
        Item1,
        
        [System.Xml.Serialization.XmlEnumAttribute("2")]
        Item2,
        
        [System.Xml.Serialization.XmlEnumAttribute("3")]
        Item3,
        
        [System.Xml.Serialization.XmlEnumAttribute("4")]
        Item4,
        
        [System.Xml.Serialization.XmlEnumAttribute("5")]
        Item5,
        
        [System.Xml.Serialization.XmlEnumAttribute("6")]
        Item6,
        
        [System.Xml.Serialization.XmlEnumAttribute("7")]
        Item7,
        
        [System.Xml.Serialization.XmlEnumAttribute("8")]
        Item8,
        
        [System.Xml.Serialization.XmlEnumAttribute("9")]
        Item9,
        
        [System.Xml.Serialization.XmlEnumAttribute("10")]
        Item10,
        
        [System.Xml.Serialization.XmlEnumAttribute("11")]
        Item11,
        
        [System.Xml.Serialization.XmlEnumAttribute("12")]
        Item12,
        
        [System.Xml.Serialization.XmlEnumAttribute("13")]
        Item13,
        
        [System.Xml.Serialization.XmlEnumAttribute("14")]
        Item14,
        
        [System.Xml.Serialization.XmlEnumAttribute("15")]
        Item15,
        
        [System.Xml.Serialization.XmlEnumAttribute("16")]
        Item16,
        
        [System.Xml.Serialization.XmlEnumAttribute("17")]
        Item17,
        
        [System.Xml.Serialization.XmlEnumAttribute("18")]
        Item18,
        
        [System.Xml.Serialization.XmlEnumAttribute("19")]
        Item19,
        
        [System.Xml.Serialization.XmlEnumAttribute("20")]
        Item20,
        
        [System.Xml.Serialization.XmlEnumAttribute("21")]
        Item21,
        
        [System.Xml.Serialization.XmlEnumAttribute("22")]
        Item22,
        
        [System.Xml.Serialization.XmlEnumAttribute("99")]
        Item99,
        
        [System.Xml.Serialization.XmlEnumAttribute("5008")]
        Item5008,
        
        [System.Xml.Serialization.XmlEnumAttribute("5009")]
        Item5009,
        
        [System.Xml.Serialization.XmlEnumAttribute("9006")]
        Item9006,
        
        [System.Xml.Serialization.XmlEnumAttribute("16384")]
        Item16384,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="dt.PrescriptionPostDated", Namespace="http://www.e-mis.com/emisopen")]
    public partial class dtPrescriptionPostDated {
        
        [System.Xml.Serialization.XmlElementAttribute(DataType="date")]
        public System.DateTime postDatedTo;
        
        public string hl7MessageXml;
        
        public string signatureXml;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.EpisodeProblemLink", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001EpisodeProblemLink : dtdbo {
        
        public System.Guid problem;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.Attachment", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001Attachment : dtdbo {
        
        public System.Guid id;
        
        public vocAttachmentType attachmentType;
        
        public System.Guid uploadedUserInRole;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool uploadedUserInRoleSpecified;
        
        public System.DateTime uploadedTime;
        
        public dtDocumentData data;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AttachmentType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAttachmentType {
        
        WORD,
        
        JPEG,
        
        GIF,
        
        TIF,
        
        BMP,
        
        XML,
        
        RTF,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.DocumentType", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001DocumentType {
        
        public System.Guid id;
        
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool idSpecified;
        
        public string displayName;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="OpenHR001.UserComment", Namespace="http://www.e-mis.com/emisopen")]
    public partial class OpenHR001UserComment {
        
        public string comment;
        
        public System.Guid userInRole;
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.EncounterHeading", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocEncounterHeading {
        
        ADD,
        
        PROB,
        
        HIST,
        
        EXAM,
        
        FH,
        
        SOC,
        
        COM,
        
        RES,
        
        MED,
        
        DRY,
        
        TR,
        
        REF,
        
        ALL,
        
        RA,
        
        SS,
        
        PR,
        
        HTGC,
        
        TP,
        
        CM,
        
        SERV,
        
        AT,
        
        ATD,
        
        RFC,
        
        HSS,
        
        AS,
        
        HN,
        
        AP,
        
        PC,
        
        VAL,
        
        TEMP,
        
        PRO,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.EDIResultStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocEDIResultStatus {
        
        FIN,
        
        INT,
        
        NA,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.OrderPriority", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocOrderPriority {
        
        H,
        
        M,
        
        L,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AptStatus", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAptStatus {
        
        SA,
        
        SB,
        
        PA,
        
        PSI,
        
        PL,
        
        DNA,
        
        PWO,
        
        VIS,
        
        TC,
        
        TNI,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AptBookingMethod", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAptBookingMethod {
        
        PRAC,
        
        EACC,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.RegistartionTemplateType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocRegistartionTemplateType {
        
        VIEW,
        
        EDIT,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.PopulationFolderSource", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocPopulationFolderSource {
        
        ORG,
        
        QOF,
        
        EMIS,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.PopulationType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocPopulationType {
        
        PATIENT,
        
        APPOINTMENT,
        
        USER,
        
        DRUG,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.PopulationParentType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocPopulationParentType {
        
        ALL,
        
        ACTIVE,
        
        POP,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.SearchDateType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocSearchDateType {
        
        BASELINE,
        
        REFERENCE,
        
        TODAY,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.MemberOperator", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocMemberOperator {
        
        AND,
        
        OR,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.RuleAction", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocRuleAction {
        
        NEXT,
        
        SELECT,
        
        REJECT,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ColumnValueInNotIn", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocColumnValueInNotIn {
        
        IN,
        
        NOTIN,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.RangeFromOperator", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocRangeFromOperator {
        
        GT,
        
        GTEQ,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.RangeToOperator", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocRangeToOperator {
        
        LT,
        
        LTEQ,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ValueUnit", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocValueUnit {
        
        DATE,
        
        YEAR,
        
        MONTH,
        
        WEEK,
        
        DAY,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.BandTimespanUnit", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocBandTimespanUnit {
        
        DAYS,
        
        WEEKS,
        
        MONTHS,
        
        YEARS,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.BandDateTimeUnit", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocBandDateTimeUnit {
        
        DATETIME,
        
        DATE,
        
        YEAR,
        
        MONTH,
        
        WEEKOFYEAR,
        
        DAYOFWEEK,
        
        HOUR,
        
        MINUTE,
        
        HOURMINUTE,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.BandingUnit", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocBandingUnit {
        
        YEAR,
        
        HOUR,
        
        WEEKOFYEAR,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.SingleValueConstant", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocSingleValueConstant {
        
        SEARCHDATE,
        
        TODAY,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.OrderDirection", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocOrderDirection {
        
        ASC,
        
        DESC,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.ListGroupSummary", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocListGroupSummary {
        
        count,
        
        exists,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AggregateValue", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAggregateValue {
        
        populationCount,
        
        recordCount,
        
        percentOfBasePop,
        
        percentOfSubPop,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.SchedulePattern", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocSchedulePattern {
        
        ONCE,
        
        DAY,
        
        WEEK,
        
        MONTH,
        
        YEAR,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AptRecurrencePattern", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAptRecurrencePattern {
        
        HOUR,
        
        DAY,
        
        WEEK,
        
        MONTH,
        
        YEAR,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.DayOfWeek", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocDayOfWeek {
        
        MON,
        
        TUE,
        
        WED,
        
        THU,
        
        FRI,
        
        SAT,
        
        SUN,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.SchedulePatternOccurrence", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocSchedulePatternOccurrence {
        
        [System.Xml.Serialization.XmlEnumAttribute("1ST")]
        Item1ST,
        
        [System.Xml.Serialization.XmlEnumAttribute("2ND")]
        Item2ND,
        
        [System.Xml.Serialization.XmlEnumAttribute("3RD")]
        Item3RD,
        
        [System.Xml.Serialization.XmlEnumAttribute("4TH")]
        Item4TH,
        
        LAST,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.AptMonthWeek", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocAptMonthWeek {
        
        [System.Xml.Serialization.XmlEnumAttribute("1ST")]
        Item1ST,
        
        [System.Xml.Serialization.XmlEnumAttribute("2ND")]
        Item2ND,
        
        [System.Xml.Serialization.XmlEnumAttribute("3RD")]
        Item3RD,
        
        [System.Xml.Serialization.XmlEnumAttribute("4TH")]
        Item4TH,
        
        [System.Xml.Serialization.XmlEnumAttribute("5TH")]
        Item5TH,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.MonthOfYear", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocMonthOfYear {
        
        JAN,
        
        FEB,
        
        MAR,
        
        APR,
        
        MAY,
        
        JUN,
        
        JUL,
        
        AUG,
        
        SEP,
        
        OCT,
        
        NOV,
        
        DEC,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.InputAction", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocInputAction {
        
        number,
        
        date,
        
        readcode,
        
        drugcode,
        
        age,
        
        time,
        
        user,
        
        sessionholder,
        
        appointment,
        
        location,
        
        values,
        
        freetext,
        
        usertype,
        
        locationtype,
        
        allergycode,
        
        speciality,
        
        organisation,
        
        ethnicorigin,
        
        sc_commissioner,
        
        sc_provider,
        
        sc_hrg,
        
        sc_consultant,
        
        sc_speciality,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.BandingHierarchyType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocBandingHierarchyType {
        
        drugChapter,
        
        readCode,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.InstanceType", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocInstanceType {
        
        DEFAULT,
        
        HOW_AM_I_DRIVING,
        
        LOOK_AHEAD,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.EnterpriseReportingLevel", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocEnterpriseReportingLevel {
        
        AGGREGATE,
        
        PSEUDO_IDENTIFYING,
        
        PATIENT_LEVEL,
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("XsdCodeGenerator", "1.0.0.0")]
    [System.Xml.Serialization.XmlTypeAttribute(TypeName="voc.Region", Namespace="http://www.e-mis.com/emisopen")]
    public enum vocRegion {
        
        ENGLAND,
        
        SCOTLAND,
        
        [System.Xml.Serialization.XmlEnumAttribute("NORTHERN IRELAND")]
        NORTHERNIRELAND,
        
        GUERNSEY,
        
        [System.Xml.Serialization.XmlEnumAttribute("REPUBLIC OF IRELAND")]
        REPUBLICOFIRELAND,
        
        JERSEY,
        
        [System.Xml.Serialization.XmlEnumAttribute("ISLE OF MAN")]
        ISLEOFMAN,
        
        EMIRATES,
        
        WALES,
    }
}
