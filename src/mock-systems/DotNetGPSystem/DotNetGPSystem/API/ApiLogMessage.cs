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

        public string HttpMethodAndPath
        {
            get
            {
                string httpRequestLine = ((RequestMessage ?? string.Empty).Split(new string[] { Environment.NewLine }, StringSplitOptions.RemoveEmptyEntries).FirstOrDefault() ?? string.Empty);

                int indexOf = httpRequestLine.LastIndexOf(" HTTP/");

                if (indexOf > 0)
                    httpRequestLine = httpRequestLine.Substring(0, indexOf);

                return httpRequestLine;
            }
        }
    }
}
