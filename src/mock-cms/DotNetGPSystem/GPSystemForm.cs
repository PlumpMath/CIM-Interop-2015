using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.ServiceModel;
using System.ServiceModel.Description;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Schema;

namespace DotNetGPSystem
{
    internal partial class GPSystemForm : Form
    {
        private TabPage _tasksTabPage;

        public GPSystemForm()
        {
            InitializeComponent();

            btnOpenPatientRecord.Click += (sender, e) => OpenPatientRecord();
            btnViewTasks.Click += (sender, e) => OpenTasks();
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
            using (PatientFindForm patientFindForm = new PatientFindForm(DataStore.OpenHRPatients))
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
                            .SingleOrDefault(t => (t as PatientTabPage).Patient == patient);

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
            {
                _tasksTabPage = new TabPage("Tasks");
                tcTabControl.TabPages.Add(_tasksTabPage);
            }

            tcTabControl.SelectedTab = _tasksTabPage;
        }

        private void llServiceStatus_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            Process.Start(llServiceStatus.Text);
        }

        private void StartApiService()
        {
            try
            {
                GPApiServiceHost gpApiServiceHost = new GPApiServiceHost();
                gpApiServiceHost.StartService();

                lblServiceStatus.Text = "RUNNING";
                lblServiceStatus.ForeColor = Color.Green;
            }
            catch (Exception exception)
            {
                lblServiceStatus.Text = "NOT STARTED";
                lblServiceStatus.ForeColor = Color.Red;

                string message = "The GP API service could not start." + Environment.NewLine + Environment.NewLine;

                if (exception is AddressAccessDeniedException)
                {
                    message += "Please re-run the GP demonstrator as administrator" + Environment.NewLine
                        + "or execute the following at an administrative command prompt:" + Environment.NewLine 
                        + Environment.NewLine
                        + "netsh http add urlacl url=http://+:9001/GPApiService user=\"" + Environment.UserName + "\"";
                }
                else
                {
                    message += exception.Message;
                }

                MessageBox.Show(this, message, MessageBoxIcon.Warning);
            }
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            this.timer.Enabled = false;

            StartApiService();
        }
    }
}
