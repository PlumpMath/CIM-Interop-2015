using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Schema;

namespace DotNetGPSystem
{
    public delegate void ExternalUpdateReceivedHandler(DateTime dateStamp, string openHRXml);

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
        private static List<KeyValuePair<DateTime, OpenHRPatient>> _patientChangeList = new List<KeyValuePair<DateTime, OpenHRPatient>>();
        private static List<KeyValuePair<DateTime, string>> _externalUpdateList = new List<KeyValuePair<DateTime, string>>();

        public static event ExternalUpdateReceivedHandler ExternalUpdateReceived;
        
        public static OpenHRPatient[] OpenHRPatients
        {
            get
            {
                if (_openHRPatients == null)
                    _openHRPatients = LoadOpenHRPatients();

                return _openHRPatients;
            }
        }

        public static OpenHRPatient[] GetPatients(string odsCode)
        {
            return OpenHRPatients
                .Where(t => t.Organisation.nationalPracticeCode == odsCode)
                .ToArray();
        }

        public static KeyValuePair<DateTime, OpenHRPatient>[] GetPatientChangeList(string odsCode)
        {
            return _patientChangeList
                .Where(t => t.Value.Organisation.nationalPracticeCode == odsCode)
                .ToArray();
        }

        public static void SaveOpenHRPatient(OpenHRPatient patient)
        {
            _patientChangeList.Add(new KeyValuePair<DateTime, OpenHRPatient>(DateTime.Now, patient));
        }

        public static void ProcessExternalUpdate(string openHRXml)
        {
            DateTime dateStamp = DateTime.Now;
            
            _externalUpdateList.Add(new KeyValuePair<DateTime, string>(dateStamp, openHRXml));

            OnExternalUpdateReceived(dateStamp, openHRXml);
        }

        private static void OnExternalUpdateReceived(DateTime dateStamp, string openHRXml)
        {
            if (ExternalUpdateReceived != null)
                ExternalUpdateReceived(dateStamp, openHRXml);
        }

        private static OpenHRPatient[] LoadOpenHRPatients()
        {
            return GetOpenHRFiles()
                .Select(t => new OpenHRPatient(t))
                .ToArray();
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
