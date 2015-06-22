using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class Slot
    {
        public Slot(Session session, int time)
        {
            Session = session;
            Time = time;
        }
        
        public int Time { get; private set; }
        public OpenHRPatient Patient { get; set; }
        public Session Session { get; private set; }
    }
}
