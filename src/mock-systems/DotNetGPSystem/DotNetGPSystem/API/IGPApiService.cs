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
        string GetPatientDemographics(string odsCode, string nhsNumber);
        
        [OperationContract]
        string GetPatient(string odsCode, Guid patientGuid);

        [OperationContract]
        Guid[] GetChangedPatients(string odsCode, DateTime sinceDateTime);

        [OperationContract]
        void UpdatePatient(string odsCode, string openHRXml);

        [OperationContract]
        string GetAppointmentSessions(string odsCode, DateTime fromDate, DateTime toDate);
        
        [OperationContract]
        string GetSlotsForSession(string odsCode, int sessionId);

        [OperationContract]
        string GetPatientAppointments(string odsCode, Guid patientGuid, DateTime fromDate, DateTime toDate);

        [OperationContract]
        bool BookAppointment(string odsCode, int slotId, Guid patientGuid, string reason);

        [OperationContract]
        string GetUserByID(string odsCode, int userInRoleId);

        [OperationContract]
        string GetOrganisationInformation(string odsCode);
    }       
}
