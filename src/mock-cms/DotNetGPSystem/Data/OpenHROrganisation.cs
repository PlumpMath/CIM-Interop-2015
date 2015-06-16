using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class OpenHROrganisation
    {
        public OpenHROrganisation(OpenHR001Organisation organisation, OpenHRUser[] users)
        {
            Organisation = organisation;
            Users = users;
        }

        public OpenHR001Organisation Organisation { get; private set; }
        public OpenHRUser[] Users { get; private set; }
    }
}
