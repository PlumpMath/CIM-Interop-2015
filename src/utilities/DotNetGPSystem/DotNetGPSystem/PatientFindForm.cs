using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DotNetGPSystem
{
    internal partial class PatientFindForm : Form
    {
        public static OpenHRPatient ChoosePatient()
        {
            using (PatientFindForm patientFindForm = new PatientFindForm(DataStore.OpenHRPatients))
                if (patientFindForm.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                    return patientFindForm.SelectedPatient;

            return null;
        }
        
        private OpenHRPatient[] _patientRecords;
        
        private PatientFindForm()
        {
            InitializeComponent();
        }

        public PatientFindForm(OpenHRPatient[] patientRecords) : this()
        {
            _patientRecords = patientRecords
                .OrderBy(t => t.Organisation.name)
                .ThenBy(t => t.Person.surname.ToLower())
                .ToArray();
        }

        public OpenHRPatient SelectedPatient
        {
            get
            {
                if (dataGridView.SelectedRows.Count != 1)
                    return null;

                return (OpenHRPatient)dataGridView.SelectedRows.Cast<DataGridViewRow>().Single().Tag;
            }
        }

        private void PatientFindForm_Load(object sender, EventArgs e)
        {
            PopulateDataGridView();
        }

        private void PopulateDataGridView()
        {
            foreach (OpenHRPatient patientPerson in _patientRecords)
            {
                DataGridViewRow row = (DataGridViewRow)dataGridView.RowTemplate.Clone();
                row.CreateCells(dataGridView);
                
                row.SetValues(patientPerson.HealthDomainEvents.Length.ToString(),
                    patientPerson.Person.GetCuiDisplayName(), 
                    patientPerson.Person.GetCuiDobStringWithAge(),
                    patientPerson.Person.address.GetHomeAddress().GetAddressAsSingleLineString(), 
                    patientPerson.Patient.patientIdentifier.GetFormattedNhsNumber(),
                    patientPerson.Organisation.name);

                row.Tag = patientPerson;

                dataGridView.Rows.Add(row);
            }

            dataGridView.Select();
        }

        private void dataGridView_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                if (dataGridView.SelectedRows.Count == 1)
                    btnSelectPatient.PerformClick();

                e.Handled = true;
            }

            base.OnKeyDown(e);
        }

        private void dataGridView_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            btnSelectPatient.PerformClick();
        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            btnSelectPatient.Enabled = (dataGridView.SelectedRows.Count == 1);
        }
    }
}
