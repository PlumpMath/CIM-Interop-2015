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
    internal partial class PatientControl : UserControl, IKeyHandler
    {
        private DemographicsControl _demographicsPage;
        private ConditionsControl _conditionsPage;
        private ConsultationsControl _consultationsPage;
        private MedicationControl _medicationPage;
        private AllEventsControl _allEventsPage;
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
            this.lblOrganisation.Text = patient.Organisation.name;
        }

        private void CreatePages(OpenHRPatient patient)
        {
            _demographicsPage = new DemographicsControl(patient, RefreshDemographicsOnUpdateEvent);
            _demographicsPage.Parent = pnlContent;
            btnDemographics.Tag = _demographicsPage;

            _conditionsPage = new ConditionsControl(patient);
            _conditionsPage.Parent = pnlContent;
            btnConditions.Tag = _conditionsPage;

            _consultationsPage = new ConsultationsControl(patient);
            _consultationsPage.Parent = pnlContent;
            btnConsultations.Tag = _consultationsPage;

            _medicationPage = new MedicationControl(patient);
            _medicationPage.Parent = pnlContent;
            btnMedication.Tag = _medicationPage;

            _allEventsPage = new AllEventsControl(patient);
            _allEventsPage.Parent = pnlContent;
            btnAllEvents.Tag = _allEventsPage;

            _openHRPage = new OpenHRControl(patient);
            _openHRPage.Parent = pnlContent;
            btnViewOpenHR.Tag = _openHRPage;
        }

        private void RefreshDemographicsOnUpdateEvent(OpenHRPatient patient)
        {
            PopulatePrecis(patient);

            _openHRPage.PopulatePatient(patient);
            
            if (_demographicsUpdatedCallback != null)
                _demographicsUpdatedCallback(patient);
        }

        private void CheckToolStripButton(ToolStripButton button)
        {
            button.Checked = true;
            
            foreach (ToolStripItem item in toolStrip1.Items)
                if (item is ToolStripButton)
                    if (item != button)
                        (item as ToolStripButton).Checked = false;
        }

        private void toolStripButton_Click(object sender, EventArgs e)
        {
            ToolStripButton button = (ToolStripButton)sender;

            Control control = (Control)button.Tag;

            if (control != null)
            {
                control.BringToFront();
                control.Focus();
            }

            CheckToolStripButton(button);
        }

        private void CheckNextToolStripItem(ArrowDirection direction)
        {
            ToolStripButton button = toolStrip1
                .Items
                .Cast<ToolStripButton>()
                .FirstOrDefault(t => t.Checked);

            if (button != null)
            {
                ToolStripButton nextButton = (ToolStripButton)toolStrip1.GetNextItem(button, direction);

                if (nextButton != null)
                    nextButton.PerformClick();
            }
        }

        public bool ProcessKey(Keys key)
        {
            switch (key)
            {
                case Keys.Left: CheckNextToolStripItem(ArrowDirection.Left); return true;
                case Keys.Right: CheckNextToolStripItem(ArrowDirection.Right); return true;
                default: return false;
            }
        }
    }
}
