using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DotNetGPSystem
{
    internal partial class AllEventsControl : UserControl
    {
        private OpenHRPatient _patient;
        
        private AllEventsControl()
        {
            InitializeComponent();

            this.Dock = DockStyle.Fill;
        }

        public AllEventsControl(OpenHRPatient patient) : this()
        {
            _patient = patient;

            PopulateEvents();
        }

        private void PopulateEvents()
        {
            if (_patient.OpenHealthRecord.healthDomain == null)
                return;

            if (_patient.OpenHealthRecord.healthDomain.@event == null)
                return;

            foreach (OpenHR001HealthDomainEvent healthEvent in _patient.HealthDomainEvents.OrderByDescending(t => t.effectiveTime.value))
            {
                string eventType = healthEvent.eventType.GetEventTypeDescription();
                string effectiveTime = healthEvent.effectiveTime.GetFormattedDate();
                string displayTerm = healthEvent.displayTerm;
                string code = healthEvent.code.WhenNotNull(t => t.code);
                string description = healthEvent.GetAssociatedTextWithValue().Trim();
                
                dataGridView.Rows.Add(eventType, effectiveTime, code, displayTerm, description);
            }
        }
    }
}
