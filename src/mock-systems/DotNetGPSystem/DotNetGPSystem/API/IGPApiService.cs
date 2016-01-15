using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    [ServiceContract]       
    public interface IGPApiService       
    {
        // Supplier wide services
        
        [OperationContract]
        [FaultContract(typeof(ApiFault))]
        string[] TracePatientByNhsNumber(string nhsNumber);

        [OperationContract]
        [FaultContract(typeof(ApiFault))]
        string[] TracePatientByDemographics(string surname, vocSex sex, DateTime dateOfBirth, string forename = null, string postcode = null);

        
        // Organisation services

        [OperationContract]
        string GetPatientDemographics(string odsCode, Guid patientGuid);
        
        [OperationContract]
        string GetPatientDemographicsByNhsNumber(string odsCode, string nhsNumber);
        
        [OperationContract]
        string GetPatient(string odsCode, Guid patientGuid);

        [OperationContract]
        string GetPatientByNhsNumber(string odsCode, string nhsNumber);

        [OperationContract]
        Guid[] GetChangedPatientIds(string odsCode, DateTime? sinceDateTime);

        [OperationContract]
        string[] GetChangedPatients(string odsCode, DateTime? sinceDateTime);

        [OperationContract]
        void UpdatePatient(string odsCode, string openHRXml);

        [OperationContract]
        string GetAppointmentSessions(string odsCode, DateTime fromDate, DateTime toDate);
        
        [OperationContract]
        string GetSlotsForSession(string odsCode, int sessionId);

        [OperationContract]
        string GetPatientAppointments(string odsCode, Guid patientGuid, DateTime fromDate, DateTime toDate);

        [OperationContract]
        string BookAppointment(string odsCode, int slotId, Guid patientGuid, string reason);

        [OperationContract]
        string CancelAppointment(string odsCode, int slotId, Guid patientGuid);

        [OperationContract]
        string GetUserByID(string odsCode, int userInRoleId);

        [OperationContract]
        string GetUserByUserInRoleGuid(string odsCode, Guid userInRoleGuid);

        [OperationContract]
        string GetOrganisationByOdsCode(string odsCode);

        [OperationContract]
        string GetOrganisationById(Guid organisationGuid);

        [OperationContract]
        string GetOrganisationInformation(string odsCode);

        [OperationContract]
        string GetLocation(string odsCode, Guid locationGuid);

        [OperationContract]
        string GetTask(string odsCode, Guid taskGuid);

        [OperationContract]
        void AddTask(string odsCode, string openHRXml);

        [OperationContract]
        string[] GetTasksByUserInRoleGuid(string odsCode, Guid userInRoleGuid);

        [OperationContract]
        string[] GetTasksByOrganisation(string odsCode);

        [OperationContract]
        string[] GetTasksByPatientGuid(string odsCode, Guid patientGuid);
    }
}
