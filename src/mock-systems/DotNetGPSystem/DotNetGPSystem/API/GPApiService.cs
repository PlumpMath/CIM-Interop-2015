﻿using System;
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
        // Supplier wide services

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

        // Organisation services

        public string GetPatientDemographics(string odsCode, Guid patientGuid)
        {
            OpenHRPatient patient = DataStore.GetPatient(odsCode, patientGuid);

            if (patient == null)
                return string.Empty;

            return patient.OpenHRExcludingHealthDomainXml;
        }

        public string GetPatientDemographicsByNhsNumber(string odsCode, string nhsNumber)
        {
            OpenHRPatient[] patients = DataStore
                .GetPatients(odsCode)
                .Where(t => t.Patient.patientIdentifier.GetNhsNumber() == nhsNumber)
                .ToArray();

            if (patients.Length == 0)
                return string.Empty;

            return patients.FirstOrDefault().OpenHRExcludingHealthDomainXml;
        }

        public string GetPatient(string odsCode, Guid patientGuid)
        {
            OpenHRPatient patient = DataStore
                .GetPatient(odsCode, patientGuid);

            if (patient == null)
                return string.Empty;

            return patient.OpenHRXml;

        }

        public Guid[] GetChangedPatients(string odsCode, DateTime sinceDateTime)
        {
            return DataStore
                .GetPatientChangeList(odsCode)
                .Where(t => t.Key >= sinceDateTime)
                .OrderBy(t => t.Key)
                .Select(t => new Guid(t.Value.Patient.id))
                .Distinct()
                .ToArray();
        }

        public void UpdatePatient(string odsCode, string openHRXml)
        {
            DataStore.ProcessExternalUpdate(openHRXml);
        }

        public string GetAppointmentSessions(string odsCode, DateTime fromDate, DateTime toDate)
        {
            Session[] sessions = DataStore.GetSessions(odsCode, fromDate, toDate);
            EomAppointmentSessions.AppointmentSessionList session = EomAppointmentTranform.ToEomSessionList(sessions);

            return Utilities.Serialize<EomAppointmentSessions.AppointmentSessionList>(session);
        }
        
        public string GetSlotsForSession(string odsCode, int sessionId)
        {
            Slot[] slots = DataStore.GetSlots(odsCode, sessionId);

            EomSlotsForSession.SlotListStruct slot = EomAppointmentTranform.ToEomSlotList(slots);

            return Utilities.Serialize<EomSlotsForSession.SlotListStruct>(slot);
        }

        public string GetPatientAppointments(string odsCode, Guid patientGuid, DateTime fromDate, DateTime toDate)
        {
            Slot[] slots = DataStore.GetSlotsForPatient(odsCode, patientGuid, fromDate, toDate);

            EomGetPatientAppointments.PatientAppointmentList appointmentList = EomAppointmentTranform.ToEomPatientAppointmentList(slots);

            return Utilities.Serialize<EomGetPatientAppointments.PatientAppointmentList>(appointmentList);
        }

        public string GetUserByID(string odsCode, int userInRoleId)
        {
            OpenHRUser user = DataStore
                .Users
                .FirstOrDefault(t => t.Organisation.nationalPracticeCode == odsCode
                                    && t.OpenHRUserId == userInRoleId);

            string result = string.Empty;

            if (user != null)
            {
                EomUserDetails37.UserDetails userDetails = EomUsersTransform.ToEomUserDetails(user);

                result = Utilities.Serialize<EomUserDetails37.UserDetails>(userDetails);
            }

            return result;
        }

        public string BookAppointment(string odsCode, int slotId, Guid patientGuid, string reason)
        {
            AppointmentOperationStatus appointmentOperationStatus = DataStore.BookAppointment(odsCode, slotId, patientGuid);
            return Enum.GetName(typeof(AppointmentOperationStatus), appointmentOperationStatus);
        }

        public string CancelAppointment(string odsCode, int slotId, Guid patientGuid)
        {
            AppointmentOperationStatus appointmentOperationStatus = DataStore.CancelAppointment(odsCode, slotId, patientGuid);
            return Enum.GetName(typeof(AppointmentOperationStatus), appointmentOperationStatus);
        }

        public string GetOrganisation(string odsCode)
        {
            OpenHROrganisation organisation = DataStore
                .Organisations
                .FirstOrDefault(t => t.Organisation.nationalPracticeCode == odsCode
                                    && (!string.IsNullOrEmpty(odsCode)));

            string result = string.Empty;

            if (organisation != null)
                result = Utilities.Serialize(organisation.Organisation);

            return result;
        }

        public string GetOrganisationInformation(string odsCode)
        {
            OpenHROrganisation organisation = DataStore
                .Organisations
                .FirstOrDefault(t => t.Organisation.nationalPracticeCode == odsCode
                                    && (!string.IsNullOrEmpty(odsCode)));

            string result = string.Empty;

            if (organisation != null)
            {
                EomOrganisationInformation.OrganisationInformation organisationInformation = EomOrganisationTransform.ToEomOrganisationInformation(organisation);

                result = Utilities.Serialize<EomOrganisationInformation.OrganisationInformation>(organisationInformation);
            }

            return result;
        }

        public string GetLocation(String odsCode, Guid locationGuid)
        {
            OpenHROrganisation organisation = DataStore
                .Organisations
                .FirstOrDefault(t => t.Organisation.nationalPracticeCode == odsCode
                                     && (!string.IsNullOrEmpty(odsCode)));

            OpenHR001Location location = organisation
                .Locations
                .Select(loc => loc.Location)
                .FirstOrDefault(loc => new Guid(loc.id).Equals(locationGuid));

            string result = string.Empty;

            if (location != null)
                result = Utilities.Serialize(location);

            return result;
        }

        public string GetTask(string odsCode, Guid taskGuid)
        {
            OpenHR001PatientTask task = DataStore
                .GetTask(odsCode, taskGuid);

            string result = string.Empty;
            if (task != null)
                result = Utilities.Serialize(task);

            return result;
        }

        public void AddTask(string odsCode, string openHRXml)
        {
            OpenHR001OpenHealthRecord record = Utilities.Deserialize<OpenHR001OpenHealthRecord>(openHRXml);
            DataStore.AddTasks(odsCode, record.healthDomain.task);
        }
    }
}
