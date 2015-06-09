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
        public string TracePatient(TraceCriteria criteria)
        {
            if (criteria == null)
                throw new ArgumentNullException("criteria");

            if (!string.IsNullOrEmpty(criteria.NhsNumber))
            {
                // trace by nhs number
            }
            else
            {
                // trace by demographics
            }

            return string.Empty;
        }

        public string GetPatientDemographics(string nhsNumber)
        {
            OpenHRPatient[] patients = DataStore
                .OpenHRPatients
                .Where(t => t.Patient.patientIdentifier.GetNhsNumber() == nhsNumber)
                .ToArray();

            if (patients.Length == 0)
                return string.Empty;

            return patients.FirstOrDefault().OpenHRExcludingHealthDomainXml;
                
        }

        public string GetPatient(Guid patientGuid)
        {
            OpenHRPatient[] patients = DataStore
                .OpenHRPatients
                .Where(t => new Guid(t.Patient.id) == patientGuid)
                .ToArray();

            if (patients.Length == 0)
                return string.Empty;

            return patients.FirstOrDefault().OpenHRXml;

        }

        public Guid[] GetChangedPatients(DateTime sinceDateTime)
        {
            return DataStore
                .PatientChangeList
                .Where(t => t.Key >= sinceDateTime)
                .OrderBy(t => t.Key)
                .Select(t => new Guid(t.Value.Patient.id))
                .Distinct()
                .ToArray();
        }

        public void UpdatePatient(string openHRXml)
        {
            DataStore.ProcessExternalUpdate(openHRXml);
        }
    }
}
