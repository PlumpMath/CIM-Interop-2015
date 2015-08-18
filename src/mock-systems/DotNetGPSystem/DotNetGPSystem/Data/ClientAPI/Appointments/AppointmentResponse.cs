using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    [Serializable]
    public class AppointmentResponse
    {
        public AppointmentResponse(AppointmentResponseStatus appointmentResponseStatus)
        {
            Code = (int)appointmentResponseStatus;
            Message = Enum.GetName(typeof(AppointmentResponseStatus), appointmentResponseStatus);
        }

        public int Code;
        public string Message;
    }
}
