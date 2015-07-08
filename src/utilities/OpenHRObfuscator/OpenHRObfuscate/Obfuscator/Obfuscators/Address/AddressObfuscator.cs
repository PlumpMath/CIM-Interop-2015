using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class AddressObfuscator : IObfuscator
    {
        private AddressList _addressList = new AddressList();
        private Dictionary<string, Address> _addressMap = new Dictionary<string, Address>();
        private Dictionary<string, string> _phoneNumberMap = new Dictionary<string, string>();

        public void Obfuscate(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            UpdateAddressFields(openHR);
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

        //private void UpdatePhoneNumberFields(OpenHR.OpenHR001OpenHealthRecord openHR)
        //{

        //    OpenHR.dtContact[] contacts = ReflectionHelper.GetObjectsOfType<OpenHR.dtContact>(openHR);

        //    OpenHR.OpenHR001PatientContact[] patientContacts = ReflectionHelper.GetObjectsOfType<OpenHR.OpenHR001PatientContact>(openHR);

        //    OpenHR.OpenHR001PatientCarer[] patientCarers = ReflectionHelper.GetObjectsOfType<OpenHR.OpenHR001PatientCarer>(openHR);

        //    OpenHR.OpenHR001EtpPrescription[] prescriptions = ReflectionHelper.GetObjectsOfType<OpenHR.OpenHR001EtpPrescription>(openHR);

        //}
    }
}
