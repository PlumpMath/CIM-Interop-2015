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
    internal partial class PatientControl : UserControl
    {
        private DemographicsControl _demographicsPage;
        private OpenHRControl _openHRPage;
        private Action<OpenHRPatient> _demographicsUpdatedCallback;
        
        private PatientControl()
        {
            InitializeComponent();
        }

        public PatientControl(OpenHRPatient patient, Action<OpenHRPatient> demographicsUpdatedCallback) : this()
        {
            this.Dock = DockStyle.Fill;
            toolStrip1.Renderer = new PatientControlToolStripRenderer();
            _demographicsUpdatedCallback = demographicsUpdatedCallback;

            PopulatePrecis(patient);
            CreatePages(patient);
        }

        private void PopulatePrecis(OpenHRPatient patient)
        {
            this.lblPrecisSurname.Text = patient.Person.GetCuiDisplayName();
            this.lblPrecisDateOfBirth.Text = patient.Person.GetCuiDobStringWithAge();
            this.lblPrecisGender.Text = patient.Person.sex.GetSexString();
            this.lblPrecisNhsNumber.Text = patient.Patient.patientIdentifier.GetFormattedNhsNumber();
        }

        private void CreatePages(OpenHRPatient patient)
        {
            _demographicsPage = new DemographicsControl(patient, RefreshDemographicsOnUpdateEvent);
            _demographicsPage.Parent = pnlContent;

            _openHRPage = new OpenHRControl(patient);
            _openHRPage.Parent = pnlContent;
        }

        private void RefreshDemographicsOnUpdateEvent(OpenHRPatient patient)
        {
            PopulatePrecis(patient);

            _openHRPage.PopulatePatient(patient);
            
            if (_demographicsUpdatedCallback != null)
                _demographicsUpdatedCallback(patient);
        }

        private void btnDemographics_Click(object sender, EventArgs e)
        {
            _demographicsPage.BringToFront();

            CheckToolStripButton((ToolStripButton)sender);
        }

        private void btnViewOpenHR_Click(object sender, EventArgs e)
        {
            _openHRPage.BringToFront();

            CheckToolStripButton((ToolStripButton)sender);
        }

        private void btnConditions_Click(object sender, EventArgs e)
        {
            CheckToolStripButton((ToolStripButton)sender);
        }

        private void btnConsultations_Click(object sender, EventArgs e)
        {
            CheckToolStripButton((ToolStripButton)sender);
        }

        private void CheckToolStripButton(ToolStripButton button)
        {
            button.Checked = true;
            
            foreach (ToolStripItem item in toolStrip1.Items)
                if (item is ToolStripButton)
                    if (item != button)
                        (item as ToolStripButton).Checked = false;
        }
    }
}
