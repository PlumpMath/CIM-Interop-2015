using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace DotNetSecondaryCareSystem
{
    internal class WebResponse
    {
        public WebResponse(string payload, HttpStatusCode statusCode)
        {
            Response = payload;
            StatusCode = statusCode;
        }
        
        public string Response { get; private set; }
        public HttpStatusCode StatusCode { get; private set; }
    }
}
