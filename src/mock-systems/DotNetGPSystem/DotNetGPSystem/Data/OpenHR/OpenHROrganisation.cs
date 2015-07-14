using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class OpenHROrganisation
    {
        public OpenHROrganisation(int organisationId, OpenHR001Organisation organisation, OpenHRLocation[] locations, OpenHRUser[] users)
        {
            OrganisationId = organisationId;
            Organisation = organisation;
            Locations = locations;
            Users = users;
        }

        public int OrganisationId { get; private set; }
        public OpenHR001Organisation Organisation { get; private set; }
        public OpenHRLocation[] Locations { get; private set; }
        public OpenHRUser[] Users { get; private set; }
    }
}
