using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class OpenHRPatient
    {
        public OpenHRPatient(int patientId, string openHRXml)
            : this(patientId, Utilities.Deserialize<OpenHR001OpenHealthRecord>(openHRXml))
        {
        }
        
        public OpenHRPatient(int patientId, OpenHR001OpenHealthRecord openHealthRecord)
        {
            PatientId = patientId;
            OpenHealthRecord = openHealthRecord;
            Patient = openHealthRecord.adminDomain.patient.Single();
            Person = openHealthRecord.adminDomain.person.Single(t => t.id == openHealthRecord.adminDomain.patient.Single().patientPerson);
            Organisation = openHealthRecord.adminDomain.organisation.Single(t => new Guid(t.id) == new Guid(openHealthRecord.author.organisation));
        }

        public OpenHR001OpenHealthRecord OpenHealthRecord { get; private set; }
        public OpenHR001Patient Patient { get; private set; }
        public OpenHR001Person Person { get; private set; }
        public OpenHR001Organisation Organisation { get; private set; }
        public int PatientId { get; private set; }

        public OpenHR001HealthDomainEvent[] HealthDomainEvents
        {
            get
            {
                return OpenHealthRecord.healthDomain.@event ?? new OpenHR001HealthDomainEvent[] { }; 
            }
        }

        public string OpenHRXml
        {
            get
            {
                return Utilities.Serialize<OpenHR001OpenHealthRecord>(OpenHealthRecord);
            }
        }

        public string OpenHRExcludingHealthDomainXml
        {
            get
            {
                OpenHR001OpenHealthRecord openHRCopy = Utilities.Deserialize<OpenHR001OpenHealthRecord>(OpenHRXml);
                openHRCopy.healthDomain = null;

                return Utilities.Serialize<OpenHR001OpenHealthRecord>(openHRCopy);

            }
        }
    }
}
