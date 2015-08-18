using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    [Serializable]
    public enum AppointmentOperationStatus : int
    {
        Successful = 0,
        SlotNotFound = -1,
        PatientNotFound = -2,
        SlotAlreadyBooked = -3,
        SlotAlreadyBookedByThisPatient = -4,
        SlotNotBookedByThisPatient = -5
    }
}
