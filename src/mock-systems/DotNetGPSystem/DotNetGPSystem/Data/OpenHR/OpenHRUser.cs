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

        public OpenHR001OpenHealthRecord CreateOpenHRDocument()
        {
            return CreateOpenHRDocument(new OpenHRUser[] { this });
        }

        public static OpenHR001OpenHealthRecord CreateOpenHRDocument(OpenHRUser[] openHRUsers)
        {
            return new OpenHR001OpenHealthRecord()
            {
                adminDomain = new OpenHR001AdminDomain()
                {
                    user = openHRUsers
                        .Select(t => t.User)
                        .DistinctBy(t => t.id)
                        .ToArray(),

                    userInRole = openHRUsers
                        .Select(t => t.UserInRole)
                        .DistinctBy(t => t.id)
                        .ToArray(),

                    person = openHRUsers
                        .Select(t => t.Person)
                        .DistinctBy(t => t.id)
                        .ToArray(),

                    role = openHRUsers
                        .Select(t => t.Role)
                        .DistinctBy(t => t.id)
                        .ToArray(),

                    organisation = openHRUsers
                        .Select(t => t.Organisation)
                        .DistinctBy(t => t.id)
                        .ToArray(),
                }
            };
        }
    }
}
