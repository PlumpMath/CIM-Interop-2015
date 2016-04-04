﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using System.Xml.Serialization;

// 
// This source code was auto-generated by xsd, Version=4.6.1055.0.
// 
namespace EomAppointmentSessions {
    using System.Xml.Serialization;


/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://www.e-mis.com/emisopen/MedicalRecord")]
[System.Xml.Serialization.XmlRootAttribute(Namespace="http://www.e-mis.com/emisopen/MedicalRecord", IsNullable=false)]
public partial class AppointmentSessionList {
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute("AppointmentSession")]
    public AppointmentSessionStruct[] AppointmentSession;
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://www.e-mis.com/emisopen/MedicalRecord")]
public partial class AppointmentSessionStruct {
    
    /// <remarks/>
    public int DBID;
    
    /// <remarks/>
    public string GUID;
    
    /// <remarks/>
    public string Name;
    
    /// <remarks/>
    public string Date;
    
    /// <remarks/>
    public string StartTime;
    
    /// <remarks/>
    public string EndTime;
    
    /// <remarks/>
    public string SlotLength;
    
    /// <remarks/>
    [System.Xml.Serialization.XmlArrayItemAttribute("SlotType", IsNullable=false)]
    public SlotsStruct[] SlotTypeList;
    
    /// <remarks/>
    public SiteStruct Site;
    
    /// <remarks/>
    [System.Xml.Serialization.XmlArrayItemAttribute("Holder", IsNullable=false)]
    public HolderStruct[] HolderList;
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://www.e-mis.com/emisopen/MedicalRecord")]
public partial class SlotsStruct {
    
    /// <remarks/>
    public string TypeID;
    
    /// <remarks/>
    public string Description;
    
    /// <remarks/>
    public int Total;
    
    /// <remarks/>
    public int Booked;
    
    /// <remarks/>
    public int Blocked;
    
    /// <remarks/>
    public int Embargoed;
    
    /// <remarks/>
    public int Available;
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://www.e-mis.com/emisopen/MedicalRecord")]
public partial class HolderStruct {
    
    /// <remarks/>
    public int DBID;
    
    /// <remarks/>
    public int RefID;
    
    /// <remarks/>
    public string Title;
    
    /// <remarks/>
    public string FirstNames;
    
    /// <remarks/>
    public string LastName;
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://www.e-mis.com/emisopen/MedicalRecord")]
public partial class SiteStruct {
    
    /// <remarks/>
    public int DBID;
    
    /// <remarks/>
    public string Name;
}
}
