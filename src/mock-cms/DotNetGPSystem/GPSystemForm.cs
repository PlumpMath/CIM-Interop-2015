using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Schema;

namespace DotNetGPSystem
{
    internal partial class GPSystemForm : Form
    {
        private OpenHRPatient[] _patientRecords;
        private TabPage _tasksTabPage;

        public GPSystemForm()
        {
            InitializeComponent();
        }

        internal OpenHRPatient[] PatientRecords
        {
            get
            {
                if (_patientRecords == null)
                    _patientRecords = DataLayer.LoadOpenHRPatients();

                return _patientRecords;
            }
        }

        private void btnOpenPatientRecord_Click(object sender, EventArgs e)
        {
            OpenPatientRecord();
        }

        private void btnViewTasks_Click(object sender, EventArgs e)
        {
            OpenTasks();
        }

        private void GPSystemForm_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.F5: btnOpenPatientRecord.PerformClick(); e.Handled = true; break;
                case Keys.F6: btnViewTasks.PerformClick(); e.Handled = true; break;
                default: break;
            }
        }

        private void OpenPatientRecord()
        {
            using (PatientFindForm patientFindForm = new PatientFindForm(PatientRecords))
            {
                if (patientFindForm.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                {
                    if (patientFindForm.SelectedPatient != null)
                    {
                        OpenHRPatient patient = patientFindForm.SelectedPatient;

                        TabPage existingPatientTabPage = tcTabControl
                            .TabPages
                            .Cast<TabPage>()
                            .Where(t => t is PatientTabPage)
                            .SingleOrDefault(t => (t as PatientTabPage).Patient.Patient.id == patient.Patient.id);

                        if (existingPatientTabPage != null)
                        {
                            tcTabControl.SelectedTab = existingPatientTabPage;
                        }
                        else
                        {
                            PatientTabPage patientTabPage = new PatientTabPage(patient);
                            tcTabControl.TabPages.Add(patientTabPage);
                            tcTabControl.SelectedTab = patientTabPage;
                        }
                    }
                }
            }
        }

        private void OpenTasks()
        {
            if (_tasksTabPage == null)
                _tasksTabPage = new TabPage("Tasks");

            if (!tcTabControl.TabPages.Contains(_tasksTabPage))
                tcTabControl.TabPages.Add(_tasksTabPage);

            tcTabControl.SelectedTab = _tasksTabPage;
        }
    }
}
