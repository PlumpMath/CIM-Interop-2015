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
        string GetCareRecord(string nhsNumber);

        [OperationContract]
        string[] GetChangedRecords(DateTime sinceDateTime);

        [OperationContract]
        void AddCondition(string nhsNumber, string conditionOpenHRXml);
    }       
}
