using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal class OpenHRLocation
    {
        public OpenHRLocation(int locationId, OpenHR001Location location)
        {
            LocationId = locationId;
            Location = location;
        }
        
        public int LocationId { get; private set; }
        public OpenHR001Location Location { get; private set; }
    }
}
