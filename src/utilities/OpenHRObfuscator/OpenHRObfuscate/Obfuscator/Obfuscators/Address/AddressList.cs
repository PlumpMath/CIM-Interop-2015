using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class AddressList
    {
        private const string ResourcePrefix = "OpenHRObfuscate.Obfuscator.Obfuscators.Address";
        private const string AddressListResourceName = ResourcePrefix + ".RandomAddresses.txt";

        private Address[] _addresses;

        private Random _random = new Random();


        public AddressList()
        {
            LoadAddresses();
        }

        private void LoadAddresses()
        {
            _addresses = Utilities.LoadStringResource(AddressListResourceName)
                .Split(new string[] { Environment.NewLine }, StringSplitOptions.RemoveEmptyEntries)
                .Select(t => GetAddress(t))
                .ToArray();
        }

        private static Address GetAddress(string address)
        {
            string[] lines = address.Split(',');

            return new Address(string.Empty,
                numberAndStreet: (lines.First() + " " + lines.Skip(1).First()).Trim(),
                locality: lines.Skip(2).First().Trim(),
                town: lines.Skip(3).First().Trim(),
                county: lines.Skip(4).First().Trim(),
                postcode: lines.Skip(5).First().Trim());
            
        }

        public Address GetRandomAddress()
        {
            if (_addresses.Length == 0)
                return null;

            int randomNumber = _random.Next(0, (_addresses.Length - 1));

            return _addresses[randomNumber];
        }
    }
}
