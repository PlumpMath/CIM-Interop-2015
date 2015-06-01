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

        private void llServiceStatus_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            Process.Start(llServiceStatus.Text);
        }

        private void StartApiService()
        {
            try
            {
                ServiceHost svcHost = new ServiceHost(typeof(GPApiService), new Uri("http://localhost:9001/GPApiService"));
                svcHost.AddServiceEndpoint(typeof(IGPApiService), new BasicHttpBinding(), "Soap");

                ServiceMetadataBehavior smb = svcHost.Description.Behaviors.Find<ServiceMetadataBehavior>();
                
                if (smb == null)
                    smb = new ServiceMetadataBehavior();

                smb.HttpGetEnabled = true;

                svcHost.Description.Behaviors.Add(smb);
                svcHost.AddServiceEndpoint(ServiceMetadataBehavior.MexContractName, MetadataExchangeBindings.CreateMexHttpBinding(), "mex");

                svcHost.Open(); 

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
