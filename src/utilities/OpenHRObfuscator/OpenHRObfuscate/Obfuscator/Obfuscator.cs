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
        private Dictionary<Guid, Guid> _guidMap = new Dictionary<Guid, Guid>();
        private Dictionary<Guid, Name> _nameMap = new Dictionary<Guid, Name>();
        private Dictionary<string, Address> _addressMap = new Dictionary<string, Address>();
        private NameList _nameList;
        private AddressList _addressList;
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

                UpdatePersonNames(openHRFile.OpenHR);
                UpdateAddressFields(openHRFile.OpenHR);
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
