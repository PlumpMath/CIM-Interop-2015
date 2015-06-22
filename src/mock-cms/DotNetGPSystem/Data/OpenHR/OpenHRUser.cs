using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DotNetGPSystem
{
    internal class OpenHRUser
    {
        public OpenHR001Person Person { get; set; }
        public OpenHR001User User { get; set; }
        public OpenHR001UserInRole UserInRole { get; set; }
        public OpenHR001Role Role { get; set; }
        public OpenHR001Organisation Organisation { get; set; }
        public bool IsSessionHolder { get; set; }

        public int OpenHRUserId { get; set; }
    }
}
