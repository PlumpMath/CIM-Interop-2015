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
        private static int _slotId = 1;

        private static int GetNextSlotId()
        {
            lock (_key)
            {
                return _slotId++;
            }
        }
        
        public static SlotListStruct ToEomSlotList(Slot[] slots)
        {
            return new SlotListStruct()
            {
                Slot = slots
                    .Select(t => ToEomSlot(t))
                    .ToArray()
            };
        }

        private static SlotStruct ToEomSlot(Slot slot)
        {
            PatientListStruct patient = null;
            
            if (slot.Patient != null)
            {
                patient = new PatientListStruct()
                {
                    Patient = new PatientStruct()
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

            return new SlotStruct()
            {
                DBID = _slotId,
                RefID = _slotId,
                Status = (slot.Patient == null) ? "Slot Available" : "Booked",
                StartTime = slot.Time.ToString().PadLeft(2, '0') + ":00",
                SlotLength = "60",
                Reason = string.Empty,
                Notes = string.Empty,
                PatientList =  patient
            };
        }
        
        public static AppointmentSessionList ToEomSessionList(Session[] sessions)
        {
            return new AppointmentSessionList()
            {
                AppointmentSession = sessions
                    .Select(t => ToEomSession(t))
                    .ToArray()
            };
        }

        private static AppointmentSessionStruct ToEomSession(Session session)
        {
            return new AppointmentSessionStruct()
            {
                Date = session.Date.ToShortDateString(),
                DBID = session.SessionId,
                
                StartTime = session
                    .Slots
                    .Min(t => t.Time)
                    .ToString()
                    .PadLeft(2, '0') + ":00",
                
                EndTime = session
                    .Slots
                    .Max(t => t.Time)
                    .ToString()
                    .PadLeft(2, '0') + ":00",

                Site = new SiteStruct()
                {
                    Name = session.Organisation.Organisation.name,
                    DBID = session.Organisation.OrganisationId
                },

                SlotLength = "60",
                
                HolderList = new HolderStruct[] 
                { 
                    ToEomHolder(session.User) 
                },

                SlotTypeList = new SlotsStruct[] 
                { 
                    new SlotsStruct()
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

        private static HolderStruct ToEomHolder(OpenHRUser user)
        {
            return new HolderStruct()
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
