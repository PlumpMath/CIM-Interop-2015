using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class Session
    {
        public Session(int sessionId, Guid sessionGuid, DateTime date, OpenHRUser user, OpenHROrganisation organisation)
        {
            SessionId = sessionId;
            SessionGuid = sessionGuid;
            Date = date;
            User = user;
            Organisation = organisation;
        }

        public int SessionId { get; private set; }
        public Guid SessionGuid { get; private set; }
        public DateTime Date { get; private set; }
        public OpenHRUser User { get; private set; }
        public OpenHROrganisation Organisation { get; private set; }

        public Slot[] Slots { get; internal set; }

        internal void CreateSlots(int[] times, int startingSlotId)
        {
            Slots = times
                .Select(t => new Slot(startingSlotId++, Guid.NewGuid(), this, t))
                .ToArray();
        }
    }
}
