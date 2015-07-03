using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class Session
    {
        public Session(int sessionId, DateTime date, OpenHRUser user, OpenHROrganisation organisation)
        {
            SessionId = sessionId;
            Date = date;
            User = user;
            Organisation = organisation;
        }

        public int SessionId { get; private set; }
        public DateTime Date { get; private set; }
        public OpenHRUser User { get; private set; }
        public OpenHROrganisation Organisation { get; private set; }

        public Slot[] Slots { get; internal set; }

        internal void CreateSlots(int[] times, int nextSlotId)
        {
            Slots = times
                .Select(t => new Slot(nextSlotId++, this, t))
                .ToArray();
        }
    }
}
