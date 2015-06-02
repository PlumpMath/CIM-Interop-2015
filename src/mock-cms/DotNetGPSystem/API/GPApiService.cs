using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class GPApiService : IGPApiService
    {
        public string GetCareRecord(string nhsNumber)
        {
            OpenHRPatient[] patients = DataLayer
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
            return new string[] { };
        }

        public void UpdateCareRecord(string openHRXml)
        {

        }
    }
}
