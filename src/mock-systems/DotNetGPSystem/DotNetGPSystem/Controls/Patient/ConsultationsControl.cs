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
    internal partial class ConsultationsControl : UserControl
    {
        private OpenHRPatient _patient;
        private List<DataGridViewRow> _headerRows = new List<DataGridViewRow>();

        private ConsultationsControl()
        {
            InitializeComponent();

            this.Dock = DockStyle.Fill;
        }

        public ConsultationsControl(OpenHRPatient patient) : this()
        {
            _patient = patient;

            PopulateConsultations();
        }

        private void PopulateConsultations()
        {
            OpenHR001Encounter[] encounters = _patient.OpenHealthRecord.healthDomain.encounter ?? new OpenHR001Encounter[] { };

            int[] years = encounters
                .Select(t => t.effectiveTime.value.Date.Year)
                .Distinct()
                .OrderByDescending(t => t)
                .ToArray();

            foreach (int year in years)
            {
                DataGridViewRow row = dataGridView.CloneDataGridViewRow(FontStyle.Bold, Color.AliceBlue);
                row.SetValues(year.ToString());
                _headerRows.Add(row);
                dataGridView.Rows.Add(row);

                foreach (OpenHR001Encounter encounter in encounters.Where(t => t.effectiveTime.value.Year == year).OrderByDescending(t => t.effectiveTime.value))
                {
                    DataGridViewRow consultationRow = dataGridView.CloneDataGridViewRow();

                    consultationRow.SetValues(encounter.effectiveTime.value.ToString("dd-MMM-yyyy"));
                    consultationRow.Tag = encounter;
                    dataGridView.Rows.Add(consultationRow);
                }
            }

            if (dataGridView.Rows.Count > 1)
            {
                DataGridViewRow row = dataGridView.Rows.Cast<DataGridViewRow>().Skip(1).First();
                row.Selected = true;
                dataGridView.CurrentCell = row.Cells.Cast<DataGridViewCell>().FirstOrDefault();
            }
        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            DataGridViewRow row = dataGridView.SelectedRows.Cast<DataGridViewRow>().FirstOrDefault();

            if (row != null)
            {
                if (_headerRows.Contains(row))
                {
                    row.Selected = false;
                    ClearEncounter();
                }
                else
                {
                    PopulateEncounter((OpenHR001Encounter)row.Tag);
                }
            }
        }

        private void ClearEncounter()
        {
            dataGridView1.Rows.Clear();
        }

        private void PopulateEncounter(OpenHR001Encounter encounter)
        {
            ClearEncounter();

            if (encounter.component != null)
            {
                foreach (string headingName in encounter.component.Select(t => t.heading.displayName).Distinct())
                {
                    DataGridViewRow row = dataGridView1.CloneDataGridViewRow(FontStyle.Bold, Color.AliceBlue);
                    row.SetValues(headingName);
                    _headerRows.Add(row);
                    dataGridView1.Rows.Add(row);
                    
                    OpenHR001Component[] components = encounter.component.Where(t => t.heading.displayName == headingName).ToArray();

                    foreach (OpenHR001Component component in components)
                    {
                        OpenHR001HealthDomainEvent healthEvent = _patient.HealthDomainEvents.FirstOrDefault(t => new Guid(t.id) == new Guid(component.@event));

                        string eventType = healthEvent.eventType.GetEventTypeDescription();
                        string effectiveTime = healthEvent.effectiveTime.GetFormattedDate();
                        string displayTerm = healthEvent.displayTerm;
                        string code = healthEvent.code.WhenNotNull(t => t.code);
                        string description = healthEvent.GetAssociatedTextWithValue().Trim();

                        dataGridView1.Rows.Add(eventType, effectiveTime, code, displayTerm, description);
                    }
                }
            }
        }

        private void dataGridView1_SelectionChanged(object sender, EventArgs e)
        {
            DataGridViewRow row = dataGridView1.SelectedRows.Cast<DataGridViewRow>().FirstOrDefault();

            if (row != null)
                if (_headerRows.Contains(row))
                    row.Selected = false;
        }
    }
}
