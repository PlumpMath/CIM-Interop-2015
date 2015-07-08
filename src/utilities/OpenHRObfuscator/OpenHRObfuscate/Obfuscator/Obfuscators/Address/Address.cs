using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class Address
    {
        public Address(string houseNameFlatNumber, string numberAndStreet, string locality, string town, string county, string postcode)
        {
            HouseNameFlatNumber = houseNameFlatNumber;
            NumberAndStreet = numberAndStreet;
            Locality = locality;
            Town = town;
            County = county;
            Postcode = postcode;
        }
        
        public string HouseNameFlatNumber { get; private set; }
        public string NumberAndStreet { get; private set; }
        public string Locality { get; private set; }
        public string Town { get; private set; }
        public string County { get; private set; }
        public string Postcode { get; private set; }
    }
}
