using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    [DataContract]
    public class ApiFault
    {
        public ApiFault(string reason)
        {
            Reason = reason;
        }
        
        [DataMember]
        public string Reason { get; private set; }
    }
}
