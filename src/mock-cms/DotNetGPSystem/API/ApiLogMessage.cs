using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class ApiLogMessage
    {
        public ApiLogMessage(DateTime dateStamp, string requestMessage, string responseMessage)
        {
            DateStamp = dateStamp;
            RequestMessage = requestMessage;
            ResponseMessage = responseMessage;
        }
        
        public DateTime DateStamp { get; private set; }
        public string RequestMessage { get; private set; }
        public string ResponseMessage { get; private set; }
    }
}
