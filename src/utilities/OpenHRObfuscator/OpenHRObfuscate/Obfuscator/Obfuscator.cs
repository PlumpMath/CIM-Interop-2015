using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    public class Obfuscator
    {
        private NameList _nameList;
        private AddressList _addressList;
        private string[] _organisationTypesToLeaveUnmodified = new string[] { "National Health Service", "Primary Care Trust", "Strategic Health Authority", "EMIS" };
        private string[] _organisationNamePrefixes = new string[] { "Alpha", "Bravo", "Delta", "Echo", "Foxtrot", "India", "Juliet", "Kilo", "Lima", "November", "Oscar", "Quebec", "Romeo", "Sierra", "Tango", "Uniform", "Yankee", "Zulu" };
        private int _nextOrganisationNameIndex = 0;

        private Dictionary<string, string> _organisationNameMap = new Dictionary<string, string>();
        private Dictionary<Guid, Guid> _guidMap = new Dictionary<Guid, Guid>();
        private Dictionary<Guid, Name> _nameMap = new Dictionary<Guid, Name>();
        private Dictionary<string, Address> _addressMap = new Dictionary<string, Address>();
        private Dictionary<string, string> _phoneNumberMap = new Dictionary<string, string>();
        private Dictionary<int, int> _cdbMap = new Dictionary<int, int>();
        
        private Random _random = new Random(DateTime.Now.Millisecond);
        
        public Obfuscator()
        {
        }

        public void Obfuscate(OpenHRFile[] openHRFiles)
        {
            Initialise();

            foreach (OpenHRFile openHRFile in openHRFiles)
            {
                openHRFile.OpenHR = Deserialize(openHRFile.InputText);

                UpdateOrganisationAndLocations(openHRFile.OpenHR);
                UpdatePersonNames(openHRFile.OpenHR);
                UpdateAddressFields(openHRFile.OpenHR);
                //UpdatePhoneNumberFields(openHRFile.OpenHR);
                UpdateAllGuidFields(openHRFile.OpenHR);
                ReorderArrays(openHRFile.OpenHR);

                openHRFile.OutputText = Serialize(openHRFile.OpenHR);
            }
        }

        private void Initialise()
        {
            _nameList = new NameList();
            _addressList = new AddressList();
        }
        
        private void UpdateOrganisationAndLocations(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            foreach (OpenHR.OpenHR001Organisation organisation in openHR.adminDomain.organisation)
            {
                UpdateOrganisationCDB(organisation);
                
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

        //private void UpdatePhoneNumberFields(OpenHR.OpenHR001OpenHealthRecord openHR)
        //{

        //    OpenHR.dtContact[] contacts = ReflectionHelper.GetObjectsOfType<OpenHR.dtContact>(openHR);

        //    OpenHR.OpenHR001PatientContact[] patientContacts = ReflectionHelper.GetObjectsOfType<OpenHR.OpenHR001PatientContact>(openHR);

        //    OpenHR.OpenHR001PatientCarer[] patientCarers = ReflectionHelper.GetObjectsOfType<OpenHR.OpenHR001PatientCarer>(openHR);

        //    OpenHR.OpenHR001EtpPrescription[] prescriptions = ReflectionHelper.GetObjectsOfType<OpenHR.OpenHR001EtpPrescription>(openHR);

        //}

        private void UpdatePersonNames(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            foreach (OpenHR.OpenHR001Person person in openHR.adminDomain.person)
            {
                Name name = GetNameReplacement(person);

                person.title = name.Title;
                person.forenames = name.Forename;
                person.surname = name.Surname;

                if (!string.IsNullOrEmpty(person.previousSurname))
                    person.previousSurname = name.PreviousSurname;

                if (!string.IsNullOrEmpty(person.callingName))
                    person.callingName = name.Forename;

                if (!string.IsNullOrEmpty(person.birthSurname))
                    person.birthSurname = name.BirthSurname;
            }
        }

        private Name GetNameReplacement(OpenHR.OpenHR001Person person)
        {
            if (!_nameMap.ContainsKey(person.id))
            {
                Name name;

                if (person.sex == OpenHR.vocSex.F)
                    name = _nameList.GetFemaleName(person.title);
                else
                    name = _nameList.GetMaleName(person.title);

                _nameMap.Add(person.id, name);
            }

            return _nameMap[person.id];
        }

        private void UpdateAddressFields(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            OpenHR.dtAddress[] addresses = ReflectionHelper.GetObjectsOfType<OpenHR.dtAddress>(openHR);

            foreach (OpenHR.dtAddress address in addresses)
            {
                if (address != null)
                {
                    string addressLine = address.houseNameFlat + address.street + address.village + address.town + address.county + address.postCode;

                    if (!string.IsNullOrEmpty(addressLine.Trim()))
                    {
                        Address addressReplacement;

                        if (!_addressMap.ContainsKey(addressLine))
                            _addressMap[addressLine] = _addressList.GetRandomAddress();
                        
                        addressReplacement = _addressMap[addressLine];

                        address.houseNameFlat = addressReplacement.HouseNameFlatNumber;
                        address.street = addressReplacement.NumberAndStreet;
                        address.village = addressReplacement.Locality;
                        address.town = addressReplacement.Town;
                        address.county = addressReplacement.County;
                        address.postCode = addressReplacement.Postcode;
                    }
                }
            }
        }

        private void UpdateAllGuidFields(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            ReflectionHelper.ReplaceObjectsOfType<System.Guid>(openHR, ReplaceGuid);
        }

        private bool ReplaceGuid(Guid original, out Guid replacement)
        {
            if (original != Guid.Empty)
            {
                if (!_guidMap.ContainsKey(original))
                    _guidMap.Add(original, Guid.NewGuid());

                replacement = _guidMap[original];

                return true;
            }
            else
            {
                replacement = Guid.Empty;
                return false;
            }
        }

        private void ReorderArrays(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            ReflectionHelper.ReorderArrays(openHR);
        }

        private static OpenHR.OpenHR001OpenHealthRecord Deserialize(string xml)
        {
            return Utilities.Deserialize<OpenHR.OpenHR001OpenHealthRecord>(xml);
        }

        private static string Serialize(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            return Utilities.Serialize<OpenHR.OpenHR001OpenHealthRecord>(openHR);
        }
    }
}
