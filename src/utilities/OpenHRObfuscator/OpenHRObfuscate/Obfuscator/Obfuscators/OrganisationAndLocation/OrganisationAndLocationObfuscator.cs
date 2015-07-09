using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class OrganisationAndLocationObfuscator : IObfuscator
    {
        private string[] _organisationTypesToLeaveUnmodified = new string[] { "National Health Service", "Primary Care Trust", "Strategic Health Authority", "EMIS" };
        private string[] _organisationNamePrefixes = new string[] { "Alpha", "Bravo", "Delta", "Echo", "Foxtrot", "India", "Juliet", "Kilo", "Lima", "November", "Oscar", "Quebec", "Romeo", "Sierra", "Tango", "Uniform", "Yankee", "Zulu" };
        private int _nextOrganisationNameIndex = 0;
        private Dictionary<string, string> _organisationNameMap = new Dictionary<string, string>();
        private Dictionary<int, int> _cdbMap = new Dictionary<int, int>();
        private Random _random = new Random(DateTime.Now.Millisecond);
        
        public void Obfuscate(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            UpdateOrganisationAndLocations(openHR);
        }

        private void UpdateOrganisationAndLocations(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            foreach (OpenHR.OpenHR001Organisation organisation in openHR.adminDomain.organisation)
            {
                UpdateOrganisationCDB(organisation);

                UpdateNameAndOdsCode(organisation);

                OpenHR.OpenHR001Location mainLocation = openHR.adminDomain.location.FirstOrDefault(t => t.id == organisation.mainLocation);
                OpenHR.OpenHR001Location[] branchLocations = openHR
                        .adminDomain
                        .location
                        .Where(t => organisation.locations != null)
                        .Where(t => (organisation.locations.Select(s => s.location).Contains(t.id)))
                        .ToArray();

                UpdateLocation(organisation, mainLocation, branchLocations);
            }
        }

        private void UpdateLocation(OpenHR.OpenHR001Organisation organisation, OpenHR.OpenHR001Location mainLocation, OpenHR.OpenHR001Location[] branchLocations)
        {
            if (mainLocation != null)
                mainLocation.name = organisation.name;

            int branchLocationCount = 1;

            foreach (OpenHR.OpenHR001Location branchLocation in branchLocations)
            {
                if (branchLocation == mainLocation)
                    continue;
                
                string branchLocationNumberSuffix = string.Empty;

                if (branchLocations.Length > 1)
                    branchLocationNumberSuffix = " " + (branchLocationCount++).ToString();
                
                branchLocation.name = organisation.name + " Location " + branchLocationNumberSuffix;
            }
        }

        private void UpdateNameAndOdsCode(OpenHR.OpenHR001Organisation organisation)
        {
            string organisationTypeDescription = organisation.organisationType.WhenNotNull(t => t.displayName);

            if (!_organisationTypesToLeaveUnmodified.Contains(organisationTypeDescription))
            {
                if (!_organisationNameMap.ContainsKey(organisation.name))
                    _organisationNameMap.Add(organisation.name, GetNextOrganisationName(organisationTypeDescription));

                organisation.name = _organisationNameMap[organisation.name];

                if (!string.IsNullOrEmpty(organisation.nationalPracticeCode))
                    organisation.nationalPracticeCode = organisation.name.First().ToString().ToUpper() + "99999";
            }
        }

        private string GetNextOrganisationName(string organisationTypeDescription)
        {
            string nameSuffix = organisationTypeDescription;

            if (organisationTypeDescription == "General Practice")
                nameSuffix = "Surgery";
            else if (organisationTypeDescription == "Hospital")
                nameSuffix = "Hospital";

            if (_nextOrganisationNameIndex > (_organisationNamePrefixes.Length - 1))
                _nextOrganisationNameIndex = 0;

            return _organisationNamePrefixes[_nextOrganisationNameIndex++] + " " + nameSuffix;
        }

        private void UpdateOrganisationCDB(OpenHR.OpenHR001Organisation organisation)
        {
            if (organisation.cdb < 0)
            {
                organisation.cdb = 0;
                organisation.cdbSpecified = false;
            }
            else if (organisation.cdb != 0)
            {
                if (!_cdbMap.ContainsKey(organisation.cdb))
                    _cdbMap.Add(organisation.cdb, _random.Next(10, 999) * 100);

                organisation.cdb = _cdbMap[organisation.cdb];
            }
        }
    }
}
