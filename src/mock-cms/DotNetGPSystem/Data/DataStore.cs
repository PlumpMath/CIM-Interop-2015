﻿using System;
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
        private const string _samplesPrefix = "DotNetGPSystem.Data.OpenHR.Samples";
        private const string _schemaPrefix = "DotNetGPSystem.Data.OpenHR.Schema";

        private static readonly string[] _schemaNames = new string[]
        {
            _schemaPrefix + ".OpenHR001.xsd",
            _schemaPrefix + ".datatypes.xsd",
            _schemaPrefix + ".vocabulary.xsd"
        };

        private const int _numberOfDaysToCreateSessionsFor = 56;
        private const int _maxSessionHoldersPerOrganisation = 4;
        private static OpenHRPatient[] _openHRPatients = null;
        private static List<KeyValuePair<DateTime, OpenHRPatient>> _patientChangeList = new List<KeyValuePair<DateTime, OpenHRPatient>>();
        private static List<KeyValuePair<DateTime, string>> _externalUpdateList = new List<KeyValuePair<DateTime, string>>();
        private static OpenHROrganisation[] _organisations;
        private static Session[] _sessions;
        public static readonly int[] AppointmentTimes = new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 };
        public static event ExternalUpdateReceivedHandler ExternalUpdateReceived;

        public static Session[] AppointmentSessions
        {
            get
            {
                if (_sessions == null)
                    _sessions = CreateAppointmentSessionsAndSlots();

                return _sessions;
            }
        }
        
        public static OpenHROrganisation[] Organisations
        {
            get
            {
                if (_organisations == null)
                    _organisations = GetOrganisations();

                return _organisations;
            }
        }


        public static OpenHRPatient[] OpenHRPatients
        {
            get
            {
                if (_openHRPatients == null)
                    _openHRPatients = LoadOpenHRPatients();

                return _openHRPatients;
            }
        }

        public static OpenHRUser[] Users
        {
            get
            {
                return Organisations
                    .SelectMany(t => t.Users)
                    .ToArray();
            }
        }

        private static Session[] CreateAppointmentSessionsAndSlots()
        {
            DateTime today = DateTime.Now.Date;

            List<Session> sessions = new List<Session>();
            List<DateTime> dates = new List<DateTime>();

            int sessionId = 1;
            int slotId = 1;

            for (int dayCount = 0; dayCount < _numberOfDaysToCreateSessionsFor; dayCount++)
            {
                DateTime date = today.AddDays(dayCount);

                if ((date.DayOfWeek == DayOfWeek.Saturday) || (date.DayOfWeek == DayOfWeek.Sunday))
                    continue;

                dates.Add(date);

                foreach (OpenHROrganisation organisation in Organisations)
                {
                    foreach (OpenHRUser user in organisation.Users.Where(t => t.IsSessionHolder))
                    {
                        Session session = new Session(
                            sessionId: sessionId++,
                            date: date,
                            user: user,
                            organisation: organisation);

                        session.CreateSlots(DataStore.AppointmentTimes, slotId);

                        slotId += DataStore.AppointmentTimes.Length;

                        sessions.Add(session);
                    }
                }
            }

            return sessions.ToArray();
        }

        private static OpenHROrganisation[] GetOrganisations()
        {
            List<OpenHROrganisation> structuredOrganisations = new List<OpenHROrganisation>();

            OpenHR001Organisation[] organisations = OpenHRPatients
                .SelectMany(t => t.OpenHealthRecord.adminDomain.organisation.Where(s => new Guid(s.id) == new Guid(t.OpenHealthRecord.author.organisation)))
                .DistinctBy(t => t.id)
                .OrderBy(t => t.name)
                .ToArray();

            int organisationId = 1;
            int userId = 1;

            foreach (OpenHR001Organisation organisation in organisations)
            {
                OpenHRPatient[] openHRAtOrganisation = OpenHRPatients
                    .Where(t => new Guid(t.OpenHealthRecord.author.organisation) == new Guid(organisation.id))
                    .ToArray();

                OpenHR001User[] users = openHRAtOrganisation
                    .SelectMany(t => t.OpenHealthRecord.adminDomain.user)
                    .DistinctBy(t => new Guid(t.id))
                    .ToArray();

                List<OpenHRUser> structuredUsers = new List<OpenHRUser>();

                int organisationUserCount = 1;

                foreach (OpenHR001User user in users)
                {
                    OpenHRUser structuredUser = new OpenHRUser();
                    structuredUser.OpenHRUserId = userId++;
                    structuredUser.User = user;
                    structuredUser.UserInRole = openHRAtOrganisation.SelectMany(t => t.OpenHealthRecord.adminDomain.userInRole).FirstOrDefault(t => new Guid(t.user) == new Guid(user.id));
                    structuredUser.Role = openHRAtOrganisation.SelectMany(t => t.OpenHealthRecord.adminDomain.role).FirstOrDefault(t => new Guid(t.id) == new Guid(structuredUser.UserInRole.id));
                    structuredUser.Person = openHRAtOrganisation.SelectMany(t => t.OpenHealthRecord.adminDomain.person).FirstOrDefault(t => new Guid(t.id) == new Guid(structuredUser.User.userPerson));
                    structuredUser.Organisation = organisation;
                    
                    if (organisationUserCount <= _maxSessionHoldersPerOrganisation)
                        structuredUser.IsSessionHolder = true;

                    organisationUserCount++;

                    structuredUsers.Add(structuredUser);
                }

                structuredOrganisations.Add(new OpenHROrganisation(
                        organisationId: organisationId++,
                        organisation: organisation,
                        users: structuredUsers.ToArray()));
            }

            return structuredOrganisations.ToArray();

        }

        public static OpenHRPatient[] GetPatients(string odsCode)
        {
            return OpenHRPatients
                .Where(t => t.Organisation.nationalPracticeCode == odsCode)
                .ToArray();
        }

        public static OpenHRPatient GetPatient(string odsCode, Guid patientGuid)
        {
            return GetPatients(odsCode)
                .SingleOrDefault(t => new Guid(t.Patient.id) == patientGuid);
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
            int patientId = 1;

            return GetOpenHRFiles()
                .Select(t => new OpenHRPatient(patientId++, t))
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

        public static Session[] GetSessions(string odsCode, DateTime from, DateTime to)
        {
            return AppointmentSessions
                .Where(t => t.Organisation.Organisation.nationalPracticeCode == odsCode
                            && t.Date >= from.Date 
                            && t.Date < to.Date.AddDays(1))
                .ToArray();
        }

        public static Slot[] GetSlots(string odsCode, int sessionId)
        {
            Session session = AppointmentSessions
                .FirstOrDefault(t => t.SessionId == sessionId
                                    && t.Organisation.Organisation.nationalPracticeCode == odsCode);

            if (session == null)
                return null;

            return session.Slots;
        }

        public static Slot GetSlot(string odsCode, int slotId)
        {
            return AppointmentSessions
                .Where(t => t.Organisation.Organisation.nationalPracticeCode == odsCode)
                .SelectMany(t => t.Slots)
                .FirstOrDefault(t => t.SlotId == slotId);
                
        }

        public static Slot[] GetSlotsForPatient(string odsCode, Guid patientGuid, DateTime fromDate, DateTime toDate)
        {
            return AppointmentSessions
                .Where(t => t.Organisation.Organisation.nationalPracticeCode == odsCode
                            && t.Date.Date >= fromDate.Date && t.Date.Date <= toDate.Date)
                .SelectMany(t => t.Slots)
                .Where(t => t.Patient != null)
                .Where(t => new Guid(t.Patient.Patient.id) == patientGuid)
                .ToArray();
        }
    }
}
