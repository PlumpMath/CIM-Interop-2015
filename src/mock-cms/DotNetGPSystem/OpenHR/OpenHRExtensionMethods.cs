using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    public static class OpenHRExtensionMethods
    {
        public static string GetCuiDisplayName(this OpenHR001Person person)
        {
            return person.surname.ToUpper() + ", " + person.forenames + " (" + person.title + ")";
        }

        public static string GetCuiDobString(this OpenHR001Person person)
        {
            return person.birthDate.ToString("yyyy-MMM-dd") + " (" + ((int)((DateTime.Now - person.birthDate).TotalDays / 365)).ToString() + "y)";
        }

        public static string GetSexString(this vocSex sex)
        {
            switch (sex)
            {
                case vocSex.M: return "Male";
                case vocSex.F: return "Female";
                case vocSex.I: return "Unknown";
                case vocSex.U: return "Unspecified";
                default: return string.Empty;
            }
        }

        public static string GetSingleLineAddress(this OpenHR001PersonAddress address)
        {
            string[] addressLines = new string[]
            {
                address.houseNameFlat,
                address.street,
                address.village,
                address.town,
                address.county,
                address.postCode
            };

            return string.Join(", ", addressLines.Where(t => !string.IsNullOrEmpty(t)));
        }

        public static OpenHR001PersonAddress GetHomeAddress(this OpenHR001PersonAddress[] addresses)
        {
            return addresses.FirstOrDefault(t => t.addressType == vocAddressType.H);
        }

        public static string GetPrefixedContactValue(this dtContact contact)
        {
            if (contact == null)
                return string.Empty;

            if (string.IsNullOrEmpty(contact.value))
                return string.Empty;

            return contact.contactType.ToString().ToUpper() + ": " + FormatPhoneNumber(contact.value);
        }

        public static dtContact GetFirstContact(this dtContact[] contacts, vocContactType contactType)
        {
            if (contacts == null)
                return null;

            return contacts.FirstOrDefault(t => t.contactType == contactType);
        }

        public static string GetSingleLineContacts(this dtContact[] contacts)
        {
            string homePhone = contacts.GetFirstContact(vocContactType.H).GetPrefixedContactValue();
            string workPhone = contacts.GetFirstContact(vocContactType.W).GetPrefixedContactValue();
            string mobilePhone = contacts.GetFirstContact(vocContactType.M).GetPrefixedContactValue();

            return string.Join(" ", new string[] { homePhone, workPhone, mobilePhone }.Where(t => !string.IsNullOrEmpty(t)).ToArray());
        }

        public static string GetNhsNumber(this dtPatientIdentifier[] identifiers)
        {
            if (identifiers == null)
                return string.Empty;

            dtPatientIdentifier nhsNumber = identifiers.FirstOrDefault(t => t.identifierType == vocPatientIdentifierType.NHS);

            if (nhsNumber == null)
                return string.Empty;

            return nhsNumber.value;
        }

        public static string GetFormattedNhsNumber(this dtPatientIdentifier[] identifiers)
        {
            return FormatNhsNumber(GetNhsNumber(identifiers));
        }

        public static string FormatNhsNumber(this string nhsNumber)
        {
            nhsNumber = (nhsNumber ?? string.Empty).Replace(" ", string.Empty);

            string result = string.Empty;

            if (nhsNumber.Length != 10)
                return nhsNumber;

            return nhsNumber.Substring(0, 3) + " " + nhsNumber.Substring(3, 3) + " " + nhsNumber.Substring(6, 4);
        }

        public static string FormatPhoneNumber(string phoneNumber)
        {
            string formattedPhoneNumber = null;

            if (!string.IsNullOrEmpty(phoneNumber))
            {
                System.Text.RegularExpressions.Regex area1 = new System.Text.RegularExpressions.Regex(@"^0[1-9]0");
                System.Text.RegularExpressions.Regex area2 = new System.Text.RegularExpressions.Regex(@"^01[1-9]1");

                string formatString;

                if (area1.Match(phoneNumber).Success)
                    formatString = "0{0:00 0000 0000}";
                else if (area2.Match(phoneNumber).Success)
                    formatString = "0{0:000 000 0000}";
                else
                    formatString = "0{0:0000 000000}";

                formattedPhoneNumber = string.Format(formatString, Int64.Parse(phoneNumber));
            }

            return formattedPhoneNumber;
        }
    }
}
