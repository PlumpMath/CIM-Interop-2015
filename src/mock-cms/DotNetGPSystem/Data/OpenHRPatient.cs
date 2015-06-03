using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class OpenHRPatient
    {
        public OpenHRPatient(OpenHR001OpenHealthRecord openHealthRecord, OpenHR001Patient patient, OpenHR001Person person)
        {
            OpenHealthRecord = openHealthRecord;
            Patient = patient;
            Person = person;
        }

        public OpenHR001OpenHealthRecord OpenHealthRecord { get; private set; }
        public OpenHR001Patient Patient { get; private set; }
        public OpenHR001Person Person { get; private set; }

        public string OpenHRXml
        {
            get
            {
                return Utilities.Serialize<OpenHR001OpenHealthRecord>(OpenHealthRecord);
            }
        }
    }
}
