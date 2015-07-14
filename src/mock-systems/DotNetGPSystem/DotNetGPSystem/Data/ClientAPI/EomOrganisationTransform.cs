using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal static class EomOrganisationTransform
    {
        public static EomOrganisationInformation.OrganisationInformation ToEomOrganisationInformation(OpenHROrganisation organisation)
        {
            return new EomOrganisationInformation.OrganisationInformation()
            {
                OrganisationList = new EomOrganisationInformation.LocationType[] 
                { 
                    CreateOrganisation(organisation) 
                },
                LocationTypeList = CreateLocations(organisation),
                UserList = CreateUsers(organisation)
            };
        }

        private static EomOrganisationInformation.LocationType CreateOrganisation(OpenHROrganisation organisation)
        {
            return new EomOrganisationInformation.LocationType()
            {
                DBID = organisation.OrganisationId.ToString(),
                GUID = organisation.Organisation.id,
                LocationName = organisation.Organisation.name,
                NationalCode = organisation.Organisation.nationalPracticeCode
            };
        }

        private static EomOrganisationInformation.TypeOfLocationType[] CreateLocations(OpenHROrganisation organisation)
        {
            return organisation
                .Locations
                .Select(t => new EomOrganisationInformation.TypeOfLocationType()
                {
                    DBID = t.LocationId.ToString(),
                    Description = t.Location.name,
                    GUID = t.Location.id
                })
                .ToArray();
        }

        private static EomOrganisationInformation.PersonType[] CreateUsers(OpenHROrganisation organisation)
        {
            return organisation
                .Users
                .Select(t => new EomOrganisationInformation.PersonType()
                {
                    DBID = t.OpenHRUserId.ToString(),
                    GUID = t.UserInRole.id,
                    Title = t.Person.title,
                    FirstNames = t.Person.forenames,
                    LastName = t.Person.surname
                })
                .ToArray();
        }
    }
}
