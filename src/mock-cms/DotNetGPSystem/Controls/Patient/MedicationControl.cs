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
    internal partial class MedicationControl : UserControl
    {
        private OpenHRPatient _patient;
        
        public MedicationControl()
        {
            InitializeComponent();
            this.Dock = DockStyle.Fill;

        }

        public MedicationControl(OpenHRPatient patient) : this()
        {
            _patient = patient;

            PopulateMedication();
        }

        private void PopulateMedication()
        {
            OpenHR001HealthDomainEvent[] healthEvents = _patient.HealthDomainEvents.Where(t => t.eventType == vocEventType.MED).ToArray();

            PopulateMedicationGroup(vocPrescriptionType.R.GetDescription(), healthEvents.Where(t => (t.Item as OpenHR001Medication).prescriptionType == vocPrescriptionType.R).ToArray());
            PopulateMedicationGroup(vocPrescriptionType.A.GetDescription(), healthEvents.Where(t => (t.Item as OpenHR001Medication).prescriptionType == vocPrescriptionType.A).ToArray());
            PopulateMedicationGroup(vocPrescriptionType.D.GetDescription(), healthEvents.Where(t => (t.Item as OpenHR001Medication).prescriptionType == vocPrescriptionType.D).ToArray());
            PopulateMedicationGroup(vocPrescriptionType.U.GetDescription(), healthEvents.Where(t => (t.Item as OpenHR001Medication).prescriptionType == vocPrescriptionType.U).ToArray());

        }

        private DataGridViewRow CreateDataGridViewRow(FontStyle? fontStyle = null, Color? backColor = null)
        {
            DataGridViewRow row = (DataGridViewRow)dataGridView.RowTemplate.Clone();

            if (backColor != null)
                row.DefaultCellStyle.BackColor = backColor.Value;

            if (fontStyle != null)
                row.DefaultCellStyle.Font = new Font(row.DefaultCellStyle.Font, fontStyle.Value);

            row.CreateCells(dataGridView);

            return row;
        }

        private void PopulateMedicationGroup(string groupDescription, OpenHR001HealthDomainEvent[] healthEvents)
        {
            DataGridViewRow headerRow = CreateDataGridViewRow(FontStyle.Bold, Color.AliceBlue);
            //_headerRows.Add(headerRow);
            headerRow.SetValues(groupDescription);
            dataGridView.Rows.Add(headerRow);

            if (healthEvents.Length == 0)
            {
                DataGridViewRow row = CreateDataGridViewRow(FontStyle.Italic);
                row.SetValues("No medication");
                dataGridView.Rows.Add(row);
            }
            else
            {
                var problems = healthEvents
                    .Select(t =>
                        new
                        {
                            Medication = t.Item as OpenHR001Medication,
                            Event = _patient.HealthDomainEvents.FirstOrDefault(s => s.id == t.id)
                        });

                foreach (var problem in problems.OrderByDescending(t => t.Event.effectiveTime.value))
                {
                    OpenHR001HealthDomainEvent healthEvent = problem.Event;

                    string eventType = problem.Medication.drugStatus.GetDescription();
                    string effectiveTime = healthEvent.effectiveTime.GetFormattedDate();
                    string displayTerm = healthEvent.displayTerm;
                    string code = healthEvent.code.WhenNotNull(t => t.code);
                    string text = healthEvent.GetAssociatedTextWithValue();

                    DataGridViewRow row = CreateDataGridViewRow();
                    row.SetValues(eventType, effectiveTime, code, displayTerm, text);
                    dataGridView.Rows.Add(row);
                }
            }
        }
    }
}
