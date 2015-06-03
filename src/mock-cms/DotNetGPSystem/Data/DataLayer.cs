using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Schema;

namespace DotNetGPSystem
{
    internal static class DataStore
    {
        private const string _samplesPrefix = "DotNetGPSystem.Data.Samples";
        private const string _schemaPrefix = "DotNetGPSystem.Data.Schema";

        private static readonly string[] _schemaNames = new string[]
        {
            _schemaPrefix + ".OpenHR001.xsd",
            _schemaPrefix + ".datatypes.xsd",
            _schemaPrefix + ".vocabulary.xsd"
        };

        private static OpenHRPatient[] _openHRPatients = null;
        private static List<KeyValuePair<DateTime, OpenHRPatient>> _changeList = new List<KeyValuePair<DateTime, OpenHRPatient>>();

        public static OpenHRPatient[] OpenHRPatients
        {
            get
            {
                if (_openHRPatients == null)
                    _openHRPatients = LoadOpenHRPatients();

                return _openHRPatients;
            }
        }

        public static KeyValuePair<DateTime, OpenHRPatient>[] PatientChangeList
        {
            get
            {
                return _changeList.ToArray();
            }
        }

        public static void SaveOpenHRPatient(OpenHRPatient patient)
        {
            _changeList.Add(new KeyValuePair<DateTime, OpenHRPatient>(DateTime.Now, patient));
        }

        private static OpenHRPatient[] LoadOpenHRPatients()
        {
            OpenHR001OpenHealthRecord[] openHealthRecords = GetOpenHRFiles()
                .Select(t => Utilities.Deserialize<OpenHR001OpenHealthRecord>(t))
                .ToArray();

            List<OpenHRPatient> openHRPatients = new List<OpenHRPatient>();

            foreach (OpenHR001OpenHealthRecord openHealthRecord in openHealthRecords)
            {
                OpenHRPatient openHRPatient = new OpenHRPatient(
                    openHealthRecord: openHealthRecord,
                    patient: openHealthRecord.adminDomain.patient.Single(),
                    person: openHealthRecord.adminDomain.person.Single(t => t.id == openHealthRecord.adminDomain.patient.Single().patientPerson));

                openHRPatients.Add(openHRPatient);
            }

            return openHRPatients.ToArray();
        }

        private static string[] GetOpenHRFiles()
        {
            string[] openHRFileNames = Assembly.GetExecutingAssembly().GetManifestResourceNames().Where(t => t.StartsWith(_samplesPrefix)).ToArray();

            List<string> openHRFiles = new List<string>();

            foreach (string openHRFileName in openHRFileNames)
                openHRFiles.Add(Utilities.LoadStringResource(openHRFileName));

            return openHRFiles.ToArray();
        }

        private static void ValidateOpenHR()
        {
            XmlSchemaSet schemas = Utilities.LoadSchemaResources(_schemaNames);

            string[] openHRFiles = GetOpenHRFiles();

            foreach (string xmlFile in openHRFiles)
            {
                string[] errors;
                bool result = Utilities.IsValidXml(xmlFile, schemas, out errors);

                if (!result)
                    throw new Exception(string.Join(Environment.NewLine, errors));
            }
        }
    }
}
