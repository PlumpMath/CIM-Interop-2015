using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
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
        private TabPage _tasksTabPage = new TasksTabPage();
        private ApiLogTabPage _apiLogTabPage = new ApiLogTabPage();
        private AppointmentBookTabPage _appointmentBookTabPage = new AppointmentBookTabPage();
        private PatientTabPage _patientTabPage = null;
        private const int _defaultPortNumber = 9001;
        private int _portNumber = _defaultPortNumber;

        public GPSystemForm()
        {
            InitializeComponent();

            btnOpenPatientRecord.Click += (sender, e) => OpenPatientRecord();
            btnViewTasks.Click += (sender, e) => OpenTasks();
            btnViewApiLog.Click += (sender, e) => OpenApiMessageLog();
            btnAppointmentBook.Click += (sender, e) => OpenAppointmentBook();

            if (!int.TryParse(ConfigurationManager.AppSettings.Get("portNumber"), out _portNumber))
                _portNumber = _defaultPortNumber;

            this.llServiceStatus.Text = string.Format(llServiceStatus.Text, _portNumber);
        }

        protected override bool ProcessCmdKey(ref Message msg, Keys keyData)
        {
            if (tcTabControl.SelectedTab != null)
            {
                IKeyHandler keyHandler = tcTabControl.SelectedTab as IKeyHandler;

                if (keyHandler != null)
                    if (keyHandler.ProcessKey(keyData))
                        return true;
            }
            
            switch (keyData)
            {
                case Keys.F5: btnOpenPatientRecord.PerformClick(); return true;
                case Keys.F6: btnViewTasks.PerformClick(); return true;
                case Keys.F7: btnViewApiLog.PerformClick(); return true;
                default: return base.ProcessCmdKey(ref msg, keyData);
            }
        }

        private void OpenPatientRecord()
        {
            OpenHRPatient patient = PatientFindForm.ChoosePatient();

            if (patient != null)
            {
                TabPage previousTabPage = _patientTabPage;

                _patientTabPage = new PatientTabPage(patient);
                tcTabControl.TabPages.Insert(0, _patientTabPage);
                tcTabControl.SelectedTab = _patientTabPage;

                if (previousTabPage != null)
                    tcTabControl.TabPages.Remove(previousTabPage);
            }
        }

        private void OpenTasks()
        {
            if (!tcTabControl.TabPages.Contains(_tasksTabPage))
                tcTabControl.TabPages.Add(_tasksTabPage);

            tcTabControl.SelectedTab = _tasksTabPage;
        }

        private void OpenApiMessageLog()
        {
            if (!tcTabControl.TabPages.Contains(_apiLogTabPage))
                tcTabControl.TabPages.Add(_apiLogTabPage);

            tcTabControl.SelectedTab = _apiLogTabPage;
        }

        private void OpenAppointmentBook()
        {
            if (!tcTabControl.TabPages.Contains(_appointmentBookTabPage))
                tcTabControl.TabPages.Add(_appointmentBookTabPage);

            tcTabControl.SelectedTab = _appointmentBookTabPage;
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
                gpApiServiceHost.StartService(_portNumber, _apiLogTabPage.LogMessage);

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
                        + "netsh http add urlacl url=http://+:{0}/GPApiService user=\"" + Environment.UserName + "\"";
                }
                else
                {
                    message += exception.Message;
                }

                MessageBox.Show(this, string.Format(message, _portNumber), MessageBoxIcon.Warning);
            }
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            this.timer.Enabled = false;

            StartApiService();
        }
    }
}
