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
        private TabPage _tasksTabPage = new TasksTabPage();
        private ApiLogTabPage _apiLogTabPage = new ApiLogTabPage();
        private PatientTabPage _patientTabPage = null;

        public GPSystemForm()
        {
            InitializeComponent();

            btnOpenPatientRecord.Click += (sender, e) => OpenPatientRecord();
            btnViewTasks.Click += (sender, e) => OpenTasks();
            btnViewApiLog.Click += (sender, e) => OpenApiMessageLog();
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
            using (PatientFindForm patientFindForm = new PatientFindForm(DataStore.OpenHRPatients))
            {
                if (patientFindForm.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                {
                    if (patientFindForm.SelectedPatient != null)
                    {
                        if (_patientTabPage != null)
                            tcTabControl.TabPages.Remove(_patientTabPage);
                        
                        _patientTabPage = new PatientTabPage(patientFindForm.SelectedPatient);
                        tcTabControl.TabPages.Insert(0, _patientTabPage);
                        tcTabControl.SelectedTab = _patientTabPage;
                    }
                }
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

        private void llServiceStatus_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            Process.Start(llServiceStatus.Text);
        }

        private void StartApiService()
        {
            try
            {
                GPApiServiceHost gpApiServiceHost = new GPApiServiceHost();
                gpApiServiceHost.StartService(_apiLogTabPage.LogMessage);

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
