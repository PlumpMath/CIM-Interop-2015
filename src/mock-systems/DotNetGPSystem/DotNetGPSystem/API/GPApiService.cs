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
                .Where(t => t.Person.surname.ToLower().StartsWith(surname.ToLower().Trim())
                    && t.Person.sex == sex
                    && t.Person.birthDate.Date == dateOfBirth.Date
                    && (string.IsNullOrEmpty(forename) || t.Person.forenames.ToLower().StartsWith(forename.ToLower().Replace(" ", string.Empty)))
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
            OpenHRPatient[] patients = GetPatientsByNhsNumber(odsCode, nhsNumber);

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

        public string GetPatientByNhsNumber(string odsCode, string nhsNumber)
        {
            OpenHRPatient[] patients = GetPatientsByNhsNumber(odsCode, nhsNumber);

            if (patients.Length == 0)
                return string.Empty;

            return patients.FirstOrDefault().OpenHRXml;
        }

        public OpenHRPatient[] GetPatientsByNhsNumber(string odsCode, string nhsNumber)
        {
            return DataStore
                .GetPatients(odsCode)
                .Where(t => t.Patient.patientIdentifier.GetNhsNumber() == nhsNumber)
                .ToArray();
        }

        public Guid[] GetChangedPatientIds(string odsCode, DateTime? sinceDateTime)
        {
            if (sinceDateTime == null)
            {
                return DataStore
                    .GetPatients(odsCode)
                    .Select(t => new Guid(t.Patient.id))
                    .Distinct()
                    .ToArray();
            }
            else
            {
                return DataStore
                    .GetPatientChangeList(odsCode)
                    .Where(t => t.Key >= sinceDateTime)
                    .OrderBy(t => t.Key)
                    .Select(t => new Guid(t.Value.Patient.id))
                    .Distinct()
                    .ToArray();
            }
        }

        public string[] GetChangedPatients(string odsCode, DateTime? sinceDateTime)
        {
            if (sinceDateTime == null)
            {
                return DataStore
                    .GetPatients(odsCode)
                    .Select(t => t.OpenHRExcludingHealthDomainXml)
                    .ToArray();
            }
            else
            {
                return DataStore
                    .GetPatientChangeList(odsCode)
                    .Where(t => t.Key >= sinceDateTime)
                    .OrderBy(t => t.Key)
                    .Select(t => t.Value.OpenHRExcludingHealthDomainXml)
                    .ToArray();
            }
        }

        public string UpdatePatient(string odsCode, string openHRXml)
        {
            UpdateRecordStatus response = UpdateRecordStatus.Successful;

            if (DataStore.AutomaticallyFileRecordUpdates)
            {
                response = DataStore.FileEvent(openHRXml);                
            }
            else
            {
                DataStore.ProcessExternalUpdate(openHRXml);
            }

            return Enum.GetName(typeof(UpdateRecordStatus), response);
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

        public string GetSlotsForSessions(string odsCode, Guid[] sessionIds)
        {
            Slot[] slots = (sessionIds ?? new Guid[] { }).SelectMany(t => DataStore.GetSlots(odsCode, t)).ToArray();

            EomSlotsForSession.SlotListStruct slot = EomAppointmentTranform.ToEomSlotList(slots);

            return Utilities.Serialize<EomSlotsForSession.SlotListStruct>(slot);
        }

        public string GetPatientAppointments(string odsCode, Guid patientGuid, DateTime fromDate, DateTime toDate)
        {
            Slot[] slots = DataStore.GetSlotsForPatient(odsCode, patientGuid, fromDate, toDate);

            EomGetPatientAppointments.PatientAppointmentList appointmentList = EomAppointmentTranform.ToEomPatientAppointmentList(slots);

            return Utilities.Serialize<EomGetPatientAppointments.PatientAppointmentList>(appointmentList);
        }

        public string GetUser(string odsCode, Guid userGuid)
        {
            OpenHRUser user = DataStore
                .Users
                .FirstOrDefault(t => t.Organisation.nationalPracticeCode == odsCode
                                    && new Guid(t.User.id) == userGuid);

            if (user == null)
                return string.Empty;

            OpenHR001OpenHealthRecord openHR = new OpenHR001OpenHealthRecord()
            {
                adminDomain = new OpenHR001AdminDomain()
                {
                    user = new OpenHR001User[] { user.User },
                    userInRole = new OpenHR001UserInRole[] { user.UserInRole },
                    person = new OpenHR001Person[] { user.Person },
                    role = new OpenHR001Role[] { user.Role },
                    organisation = new OpenHR001Organisation[] { user.Organisation }
                }
            };

            return Utilities.Serialize(openHR);
        }

        public string BookAppointment(string odsCode, int slotId, Guid patientGuid, string reason)
        {
            Slot slot = DataStore.GetSlot(odsCode, slotId);
            return BookAppointment2(odsCode, slot.WhenNotNull(t => t.SlotGuid), patientGuid, reason);
        }

        public string BookAppointment2(string odsCode, Guid slotGuid, Guid patientGuid, string reason)
        {
            AppointmentOperationStatus appointmentOperationStatus = DataStore.BookAppointment(odsCode, slotGuid, patientGuid);
            return Enum.GetName(typeof(AppointmentOperationStatus), appointmentOperationStatus);
        }

        public string CancelAppointment(string odsCode, int slotId, Guid patientGuid)
        {
            Slot slot = DataStore.GetSlot(odsCode, slotId);
            return CancelAppointment2(odsCode, slot.WhenNotNull(t => t.SlotGuid), patientGuid);
        }

        public string CancelAppointment2(string odsCode, Guid slotGuid, Guid patientGuid)
        {
            AppointmentOperationStatus appointmentOperationStatus = DataStore.CancelAppointment(odsCode, slotGuid, patientGuid);
            return Enum.GetName(typeof(AppointmentOperationStatus), appointmentOperationStatus);
        }

        public string GetOrganisationByOdsCode(string odsCode)
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

        public string GetOrganisationById(Guid organisationGuid)
        {
            OpenHROrganisation organisation = DataStore
                .Organisations
                .FirstOrDefault(t => new Guid(t.Organisation.id) == organisationGuid);

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

        public string[] GetTasksByUserInRoleGuid(string odsCode, Guid userInRoleGuid)
        {
            OpenHR001PatientTask[] tasks = DataStore
                .GetUserTasks(odsCode, userInRoleGuid);

            if (tasks == null)
                return null;

            return tasks
                .Select(Utilities.Serialize)
                .ToArray();
        }

        public string[] GetTasksByOrganisation(string odsCode)
        {
            OpenHR001PatientTask[] tasks = DataStore
                .GetOrganisationTasks(odsCode);

            if (tasks == null)
                return null;

            return tasks
                .Select(Utilities.Serialize)
                .ToArray();
        }

        public string[] GetTasksByPatientGuid(string odsCode, Guid patientGuid)
        {
            OpenHR001PatientTask[] tasks = DataStore
                .GetPatientTasks(odsCode, patientGuid);

            if (tasks == null)
                return null;

            return tasks
                .Select(Utilities.Serialize)
                .ToArray();
        }
    }
}
