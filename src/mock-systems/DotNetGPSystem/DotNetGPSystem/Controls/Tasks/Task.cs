using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class Task
    {
        public string OpenHRXml { get; set; }
        public DateTime DateStamp { get; set; }
        public OpenHRPatient Patient { get; set; }
        public string Display { get; set; }
        public bool CanFile { get; set; }
        public bool Filed { get; set; }
        public OpenHR001HealthDomainEvent Event { get; set; }
    }
}
