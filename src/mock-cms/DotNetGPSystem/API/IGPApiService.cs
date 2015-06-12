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
        [OperationContract]
        [FaultContract(typeof(ApiFault))]
        string[] TracePatientByNhsNumber(string nhsNumber);

        [OperationContract]
        [FaultContract(typeof(ApiFault))]
        string[] TracePatientByDemographics(string surname, vocSex sex, DateTime dateOfBirth, string forename = null, string postcode = null);

        [OperationContract]
        string GetPatientDemographics(string nhsNumber);
        
        [OperationContract]
        string GetPatient(Guid patientGuid);

        [OperationContract]
        Guid[] GetChangedPatients(DateTime sinceDateTime);

        [OperationContract]
        void UpdatePatient(string openHRXml);
    }       
}
