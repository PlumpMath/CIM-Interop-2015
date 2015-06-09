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
        string TracePatient(TraceCriteria criteria);

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
