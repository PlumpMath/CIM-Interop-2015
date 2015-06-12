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
        public string[] TracePatientByNhsNumber(string nhsNumber)
        {
            if ((nhsNumber ?? string.Empty).Replace(" ", string.Empty).Length != 10)
                throw new FaultException<ApiFault>(new ApiFault("NHS number must be ten digits"));

            string[] result = DataStore
                .OpenHRPatients
                .Where(t => t.Patient.patientIdentifier.GetNhsNumber() == nhsNumber.Replace(" ", string.Empty))
                .Select(t => t.OpenHRExcludingHealthDomainXml)
                .ToArray();

            return result;
        }

        public string[] TracePatientByDemographics(string surname, vocSex sex, DateTime dateOfBirth, string forename = null, string postcode = null)
        {
            if ((surname ?? string.Empty).Replace(" ", string.Empty).Length < 2)
                throw new FaultException<ApiFault>(new ApiFault("Two or more characters of the surname must be specified."));
            
            if (dateOfBirth == default(DateTime))
                throw new FaultException<ApiFault>(new ApiFault("Date of birth must be specified."));

            return DataStore
                .OpenHRPatients
                .Where(t => t.Person.surname.StartsWith(surname.Trim())
                    && t.Person.sex == sex
                    && t.Person.birthDate.Date == dateOfBirth.Date
                    && (string.IsNullOrEmpty(forename) || t.Person.forenames.StartsWith(forename.Replace(" ", string.Empty)))
                    && (string.IsNullOrEmpty(postcode) || t.Person.address.GetHomeAddress().postCode.ToUpper().Replace(" ", string.Empty).StartsWith(postcode.ToUpper().Replace(" ", string.Empty))))
                .Select(t => t.OpenHRExcludingHealthDomainXml)
                .ToArray();
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
