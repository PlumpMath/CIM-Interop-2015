using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    [ServiceBehavior(AddressFilterMode = AddressFilterMode.Any)] 
    internal class GPApiService : IGPApiService
    {
        public string GetCareRecord(string nhsNumber)
        {
            OpenHRPatient[] patients = DataStore
                .OpenHRPatients
                .Where(t => t.Patient.patientIdentifier.GetNhsNumber() == nhsNumber)
                .ToArray();

            if (patients.Length == 0)
                return string.Empty;

            if (patients.Length > 1)
                throw new Exception("More than one patient found matching that NHS number.");

            return patients.Single().OpenHRXml;
        }

        public string[] GetChangedRecords(DateTime sinceDateTime)
        {
            return DataStore
                .PatientChangeList
                .Where(t => t.Key >= sinceDateTime)
                .OrderBy(t => t.Key)
                .Select(t => t.Value.Patient.patientIdentifier.GetNhsNumber())
                .Distinct()
                .ToArray();
        }

        public void UpdateCareRecord(string openHRXml)
        {

        }
    }
}
