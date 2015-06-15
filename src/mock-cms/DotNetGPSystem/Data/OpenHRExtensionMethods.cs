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
            return person.birthDate.ToString("dd-MMM-yyyy");
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

        public static string GetFormattedDate(this dtDatePart date)
        {
            switch (date.datepart)
            {
                case vocDatePart.YMD: return date.value.ToString("dd-MMM-yyyy");
                case vocDatePart.YM: return date.value.ToString("MMM-yyyy");
                case vocDatePart.Y: return date.value.ToString("yyyy");
                case vocDatePart.YMDT: return date.value.ToString("dd-MMM-yyyy HH:mm");
                case vocDatePart.U: return string.Empty;
                default: throw new NotSupportedException("vocDatePart");
            }
        }

        public static string GetEventTypeDescription(this vocEventType eventType)
        {
            switch (eventType)
            {
                case vocEventType.OBS: return "Observation";
				case vocEventType.MED: return "Medication";
				case vocEventType.TR: return "Test Request";
				case vocEventType.INV: return "Investigation";
				case vocEventType.VAL: return "Value";
				case vocEventType.ISS: return "Medication Issue";
                case vocEventType.ATT: return "Attachment";
				case vocEventType.REF: return "Referral";
                case vocEventType.DRY: return "Diary";
				case vocEventType.ALT: return "Alert";
				case vocEventType.ALL: return "Allergy";
				case vocEventType.FH: return "Family history";
				case vocEventType.IMM: return "Immunisation";
				case vocEventType.REP: return "Report";
				case vocEventType.OH: return "Order Header";
                default: throw new NotSupportedException("vocEventType");
            }
        }

        public static string GetProblemSignficance(this vocProblemSignificance signficance)
        {
            switch (signficance)
            {
                case vocProblemSignificance.S: return "Signficant";
                case vocProblemSignificance.M: return "Minor";
                default: throw new NotSupportedException("vocProblemSignficance");
            }
        }

        public static string GetDescription(this vocProblemStatus status)
        {
            switch (status)
            {
                case vocProblemStatus.A: return "Active";
                case vocProblemStatus.HP: return "Health Admin";
                case vocProblemStatus.I: return "Inactive";
                case vocProblemStatus.PP: return "Potential Condition";
                default: throw new NotSupportedException("vocProblemStatus");
            }
        }

        public static string GetDescription(this vocPrescriptionType type)
        {
            switch (type)
            {
                case vocPrescriptionType.A: return "Acute";
                case vocPrescriptionType.R: return "Repeat";
                case vocPrescriptionType.D: return "Repeat Dispensed";
                case vocPrescriptionType.U: return "Automatic";
                default: throw new NotSupportedException("vocPrescriptionType");
            }
        }

        public static string GetDescription(this vocDrugStatus status)
        {
            switch (status)
            {
                case vocDrugStatus.A: return "Active";
                case vocDrugStatus.C: return "Cancelled";
                case vocDrugStatus.N: return "Never Active";
                default: throw new NotSupportedException("vocDrugStatus");
            }
        }

        public static string GetObservationValueText(this OpenHR001HealthDomainEvent healthEvent)
        {
            string result = string.Empty;
            
            OpenHR001Observation observation = (healthEvent.Item as OpenHR001Observation);

            if (observation != null)
            {
                if (observation.value != null)
                {
                    if (observation.value.Item is OpenHR001NumericValue)
                    {
                        OpenHR001NumericValue observationValue = observation.value.Item as OpenHR001NumericValue;

                        result = observationValue.value.ToString() + " " + observationValue.units;

                    }
                    else if (observation.value.Item is OpenHR001TextValue)
                    {
                        OpenHR001TextValue textValue = observation.value.Item as OpenHR001TextValue;

                        result = textValue.value;
                    }
                }
            }

            return result;
        }

        public static string[] GetAssociatedText(this OpenHR001HealthDomainEvent healthEvent)
        {
            if (healthEvent.associatedText == null)
                return new string[] { };

            return healthEvent.associatedText.Select(t => t.value).ToArray();
        }

        public static string GetAssociatedTextWithValue(this OpenHR001HealthDomainEvent healthEvent)
        {
            string value = healthEvent.GetObservationValueText();
            string[] associatedText = healthEvent.GetAssociatedText();

            List<string> descriptionText = new List<string>();

            if (!string.IsNullOrEmpty(value))
                descriptionText.Add(value);

            descriptionText.AddRange(associatedText);

            return string.Join(" | ", descriptionText);
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
