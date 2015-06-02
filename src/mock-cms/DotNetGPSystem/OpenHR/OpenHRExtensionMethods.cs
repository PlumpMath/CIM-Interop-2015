using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal static class OpenHRHelperMethods
    {
        public static string GetCuiDisplayName(this OpenHR001Person person)
        {
            return person.surname.ToUpper() + ", " + person.forenames + " (" + person.title + ")";
        }

        public static string GetCuiDobString(this OpenHR001Person person)
        {
            return person.birthDate.ToString("yyyy-MMM-dd");
        }

        public static string GetCuiDobStringWithAge(this OpenHR001Person person)
        {
            return GetCuiDobString(person) + " (" + ((int)((DateTime.Now - person.birthDate).TotalDays / 365)).ToString() + "y)";
        }

        public static DateTime GetDobFromCuiString(string cuiDob)
        {
            return DateTime.Parse(cuiDob);
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

        public static vocSex GetSexFromString(string sex)
        {
            switch (sex)
            {
                case "Male": return vocSex.M;
                case "Female": return vocSex.F;
                case "Unknown": return vocSex.I;
                default: return vocSex.U;
            }
        }

        public static string GetAddressAsSingleLineString(this OpenHR001PersonAddress address)
        {
            return GetAddressAsMultilineString(address).Replace(Environment.NewLine, ", ");
        }

        public static string GetAddressAsMultilineString(this OpenHR001PersonAddress address)
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

            return string.Join(Environment.NewLine, addressLines.Where(t => !string.IsNullOrEmpty(t)));
        }

        public static OpenHR001PersonAddress GetHomeAddress(this OpenHR001PersonAddress[] addresses)
        {
            return addresses.FirstOrDefault(t => t.addressType == vocAddressType.H);
        }

        public static void SetHomeAddress(this OpenHR001PersonAddress[] addresses, string[] addressLines)
        {
            OpenHR001PersonAddress homeAddress = addresses.GetHomeAddress();

            List<string> addressLinesCleaned = addressLines.Where(t => (!string.IsNullOrEmpty(t))).ToList();

            string postCode = addressLinesCleaned.FirstOrDefault(t => IsPostCode(t));
            addressLinesCleaned.Remove(postCode);
            
            homeAddress.houseNameFlat = addressLinesCleaned.FirstOrDefault();
            homeAddress.street = addressLinesCleaned.Skip(1).FirstOrDefault();
            homeAddress.village = addressLinesCleaned.Skip(2).FirstOrDefault();
            homeAddress.town = addressLinesCleaned.Skip(3).FirstOrDefault();
            homeAddress.county = addressLinesCleaned.Skip(4).FirstOrDefault();
            homeAddress.postCode = postCode;
        }

        public static string GetContactValueWithPrefixedDescription(this dtContact[] contacts, vocContactType contactType)
        {
            string contactValue = GetFormattedContactValue(contacts, contactType);

            if (string.IsNullOrEmpty(contactValue))
                return string.Empty;
            
            return contactType.ToString().ToUpper() + ": " + contactValue;
        }

        public static void SetContactValue(this OpenHR001Person person, vocContactType contactType, string value)
        {
            dtContact contact = (person.contact ?? new dtContact[] { }).FirstOrDefault(t => t.contactType == contactType);

            if (!string.IsNullOrEmpty(value))
            {
                if (contact == null)
                {
                    contact = new dtContact()
                    {
                        contactType = contactType,
                        updateMode = vocUpdateMode.add,
                        id = Guid.NewGuid().ToString()
                    };

                    person.contact = (person.contact ?? new dtContact[] { })
                        .Concat(new dtContact[] { contact })
                        .ToArray();
                }

                contact.value = value;
            }
            else
            {
                if (contact != null)
                {
                    person.contact = person.contact
                        .Where(t => t != contact)
                        .ToArray();
                }
            }
        }

        public static string GetFormattedContactValue(this dtContact[] contacts, vocContactType contactType)
        {
            if (contacts == null)
                return null;

            dtContact contact = contacts.FirstOrDefault(t => t.contactType == contactType);

            if (contact == null)
                return string.Empty;

            if (string.IsNullOrEmpty(contact.value))
                return string.Empty;

            return FormatContactValue(contactType, contact.value);
        }

        public static string GetSingleLineContacts(this dtContact[] contacts)
        {
            string homePhone = contacts.GetContactValueWithPrefixedDescription(vocContactType.H);
            string workPhone = contacts.GetContactValueWithPrefixedDescription(vocContactType.W);
            string mobilePhone = contacts.GetContactValueWithPrefixedDescription(vocContactType.M);

            return string.Join(" ", new string[] { homePhone, workPhone, mobilePhone }.Where(t => !string.IsNullOrEmpty(t)).ToArray());
        }

        public static void UpdateNhsNumber(this dtPatientIdentifier[] identifiers, string value)
        {
            if (identifiers == null)
                return;

            dtPatientIdentifier nhsNumber = identifiers.FirstOrDefault(t => t.identifierType == vocPatientIdentifierType.NHS);

            if (nhsNumber == null)
                return;

            nhsNumber.value = value;
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

        public static string FormatContactValue(vocContactType contactType, string value)
        {
            switch (contactType)
            {
                case vocContactType.H:
                case vocContactType.W:
                case vocContactType.M: return FormatPhoneNumber(value);
                default: return value;
            }
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

            if (phoneNumber != formattedPhoneNumber.Replace(" ", string.Empty))
                formattedPhoneNumber = phoneNumber;

            return formattedPhoneNumber;
        }

        public static bool IsPostCode(string postcode)
        {
            return Regex.IsMatch(postcode, "^(GIR 0AA)|(((A[BL]|B[ABDHLNRSTX]?|C[ABFHMORTVW]|D[ADEGHLNTY]|E[HNX]?|F[KY]|G[LUY]?|H[ADGPRSUX]|I[GMPV]|JE|K[ATWY]|L[ADELNSU]?|M[EKL]?|N[EGNPRW]?|O[LX]|P[AEHLOR]|R[GHM]|S[AEGKLMNOPRSTY]?|T[ADFNQRSW]|UB|W[ADFNRSV]|YO|ZE)[1-9]?[0-9]|((E|N|NW|SE|SW|W)1|EC[1-4]|WC[12])[A-HJKMNPR-Y]|(SW|W)([2-9]|[1-9][0-9])|EC[1-9][0-9])( [0-9][ABD-HJLNP-UW-Z]{2})?)$");
        }
    }
}
