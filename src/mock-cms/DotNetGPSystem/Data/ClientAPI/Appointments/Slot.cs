using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class Slot
    {
        public Slot(int slotId, Session session, int time)
        {
            SlotId = slotId;
            Session = session;
            Time = time;
        }

        public int SlotId { get; private set; }
        public int Time { get; private set; }
        public OpenHRPatient Patient { get; set; }
        public Session Session { get; private set; }

        public string FormattedTime
        {
            get
            {
                return Time.ToString().PadLeft(2, '0') + ":00";
            }
        }

        public int Length
        {
            get
            {
                return 60;
            }
        }

        public string Status
        {
            get
            {
                return (Patient == null) ? "Slot Available" : "Booked";
            }
        }
    }
}
