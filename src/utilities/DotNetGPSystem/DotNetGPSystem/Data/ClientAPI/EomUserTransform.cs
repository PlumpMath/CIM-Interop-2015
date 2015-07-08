using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal static class EomUsersTransform
    {
        public static EomUserDetails37.UserDetails ToEomUserDetails(OpenHRUser user)
        {
            return new EomUserDetails37.UserDetails()
            {
                Person = new EomUserDetails37.PersonType()
                {
                    DBID = user.OpenHRUserId.ToString(),
                    GUID = user.UserInRole.id,
                    Title = user.Person.title,
                    FirstNames = user.Person.forenames,
                    LastName = user.Person.surname
                }
            };
        }
    }
}
