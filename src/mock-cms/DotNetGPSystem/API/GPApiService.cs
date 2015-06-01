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
            return "<openHR></openHR>";
        }

        public string[] GetChangedRecords(DateTime sinceDateTime)
        {
            return new string[]
            {
                "1111111111"
            };
        }

        public void AddCondition(string nhsNumber, string conditionOpenHRXml)
        {

        }
    }
}
