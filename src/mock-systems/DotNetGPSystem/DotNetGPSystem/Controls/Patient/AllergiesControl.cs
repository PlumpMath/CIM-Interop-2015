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
    internal partial class AllergiesControl : UserControl
    {
        private OpenHRPatient _patient;
        private List<DataGridViewRow> _headerRows = new List<DataGridViewRow>();

        private AllergiesControl()
        {
            InitializeComponent();

            this.Dock = DockStyle.Fill;
        }

        public AllergiesControl(OpenHRPatient patient) : this()
        {
            _patient = patient;

            OpenHR001HealthDomainEvent[] allergies = _patient.OpenHealthRecord.healthDomain.@event.Where(t => t.eventType == vocEventType.ALL).ToArray();

            PopulateAllergies(allergies);
        }

        private void PopulateAllergies(OpenHR001HealthDomainEvent[] allergies)
        {
            if (allergies.Length == 0)
            {
                DataGridViewRow row = dataGridView.CloneDataGridViewRow(FontStyle.Italic);
                row.SetValues("No allergies");
                dataGridView.Rows.Add(row);
            }
            else
            {
                var problems = allergies
                    .Select(t =>
                        new
                        {
                            Problem = t,
                            Event = _patient.HealthDomainEvents.FirstOrDefault(s => s.id == t.id)
                        });

                foreach (var problem in problems.OrderByDescending(t => t.Event.effectiveTime.value))
                {
                    OpenHR001HealthDomainEvent healthEvent = problem.Event;

                    string effectiveTime = healthEvent.effectiveTime.GetFormattedDate();
                    string displayTerm = healthEvent.displayTerm;
                    string code = healthEvent.code.WhenNotNull(t => t.code);
                    string text = healthEvent.GetAssociatedTextWithValue();

                    DataGridViewRow row = dataGridView.CloneDataGridViewRow();
                    row.SetValues(effectiveTime, code, displayTerm, text);
                    dataGridView.Rows.Add(row);
                }
            }
        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            DataGridViewRow row = dataGridView.SelectedRows.Cast<DataGridViewRow>().FirstOrDefault();

            if (_headerRows.Contains(row))
                row.Selected = false;
        }
    }
}
