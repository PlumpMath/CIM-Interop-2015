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
        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            DataGridViewRow row = dataGridView.SelectedRows.Cast<DataGridViewRow>().FirstOrDefault();

            this.textBox1.Text = string.Empty;

            if (row != null)
            {
                if (_headerRows.Contains(row))
                {
                    row.Selected = false;
                }
                else
                {
                    OpenHR001Encounter encounter = (OpenHR001Encounter)row.Tag;

                    string xml = Utilities.Serialize<OpenHR001Encounter>(encounter);
                    this.textBox1.Text = xml;
                }
            }
        }

    }
}
