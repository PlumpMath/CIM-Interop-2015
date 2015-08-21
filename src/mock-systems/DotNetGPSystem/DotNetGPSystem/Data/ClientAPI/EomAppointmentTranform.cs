using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal static class EomAppointmentTranform
    {
        private static object _key = new object();
        
        public static EomGetPatientAppointments.PatientAppointmentList ToEomPatientAppointmentList(Slot[] slots)
        {
            return new EomGetPatientAppointments.PatientAppointmentList()
            {
                Appointment = slots.Select(t => ToEomPatientAppointment(t)).ToArray()
            };
        }

        public static EomGetPatientAppointments.AppointmentStruct ToEomPatientAppointment(Slot slot)
        {
            return new EomGetPatientAppointments.AppointmentStruct()
            {
                Date = slot.Session.Date.ToShortDateString(),
                Duration = slot.Length.ToString(),
                SessionDBID = slot.Session.SessionId,
                SlotID = slot.SlotId,
                StartTime = slot.FormattedTime,
                Status = slot.Status,
                SiteID = slot.Session.Organisation.OrganisationId,
                HolderList = new EomGetPatientAppointments.HolderStruct[]
                {
                    ToEomHolder2(slot.Session.User)
                }
            };
        }

        public static EomGetPatientAppointments.HolderStruct ToEomHolder2(OpenHRUser user)
        {
            return new EomGetPatientAppointments.HolderStruct()
            {
                Title = user.Person.title,
                FirstNames = user.Person.forenames,
                LastName = user.Person.surname,
                DBID = user.OpenHRUserId
            };
        }

        public static EomSlotsForSession.SlotListStruct ToEomSlotList(Slot[] slots)
        {
            return new EomSlotsForSession.SlotListStruct()
            {
                Slot = slots
                    .Select(t => ToEomSlot(t))
                    .ToArray()
            };
        }

        private static EomSlotsForSession.SlotStruct ToEomSlot(Slot slot)
        {
            EomSlotsForSession.PatientListStruct patient = null;
            
            if (slot.Patient != null)
            {
                patient = new EomSlotsForSession.PatientListStruct()
                {
                    Patient = new EomSlotsForSession.PatientStruct()
                    {
                        DBID = slot.Patient.PatientId,
                        RefID = slot.Patient.PatientId,
                        FirstNames = slot.Patient.Person.forenames,
                        Surname = slot.Patient.Person.surname,
                        Title = slot.Patient.Person.title,
                        FullName = slot.Patient.Person.GetCuiDisplayName()
                    }
                };
            }

            return new EomSlotsForSession.SlotStruct()
            {
                DBID = slot.SlotId,
                RefID = slot.SlotId,
                Status = slot.Status,
                Date = slot.Session.Date.ToShortDateString(),
                StartTime = slot.FormattedTime,
                SlotLength = slot.Length.ToString(),
                Reason = string.Empty,
                Notes = string.Empty,
                PatientList =  patient
            };
        }
        
        public static EomAppointmentSessions.AppointmentSessionList ToEomSessionList(Session[] sessions)
        {
            return new EomAppointmentSessions.AppointmentSessionList()
            {
                AppointmentSession = sessions
                    .Select(t => ToEomSession(t))
                    .ToArray()
            };
        }

        private static EomAppointmentSessions.AppointmentSessionStruct ToEomSession(Session session)
        {
            return new EomAppointmentSessions.AppointmentSessionStruct()
            {
                Date = session.Date.ToShortDateString(),
                DBID = session.SessionId,
                
                StartTime = session
                    .Slots
                    .Min(t => t.FormattedTime),
                
                EndTime = session
                    .Slots
                    .Max(t => t.FormattedTime),

                Site = new EomAppointmentSessions.SiteStruct()
                {
                    Name = session.Organisation.Organisation.name,
                    DBID = session.Organisation.OrganisationId
                },

                SlotLength = "60",

                HolderList = new EomAppointmentSessions.HolderStruct[] 
                { 
                    ToEomHolder(session.User) 
                },

                SlotTypeList = new EomAppointmentSessions.SlotsStruct[] 
                { 
                    new EomAppointmentSessions.SlotsStruct()
                    {
                        Available = session.Slots.Count(t => t.Patient == null),
                        Blocked = 0,
                        Booked = session.Slots.Count(t => t.Patient != null),
                        Description = string.Empty,
                        Embargoed = 0,
                        Total = session.Slots.Count(),
                        TypeID = string.Empty
                    }
                }
            };
        }

        private static EomAppointmentSessions.HolderStruct ToEomHolder(OpenHRUser user)
        {
            return new EomAppointmentSessions.HolderStruct()
            {
                FirstNames = user.Person.forenames,
                LastName = user.Person.surname,
                Title = user.Person.title,
                DBID = user.OpenHRUserId,
                RefID = user.OpenHRUserId
            };
        }

    }
}
