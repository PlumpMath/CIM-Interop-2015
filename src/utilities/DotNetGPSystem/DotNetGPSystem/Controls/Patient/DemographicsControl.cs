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
    internal partial class DemographicsControl : UserControl
    {
        private OpenHRPatient _patient;
        private Action<OpenHRPatient> _demographicsUpdatedCallback;

        private DemographicsControl()
        {
            InitializeComponent();

            this.Dock = DockStyle.Fill;
            this.llEditDemographics.LinkClicked += (sender, e) => EditDemographics();
            this.llSave.LinkClicked += (sender, e) => SaveDemographics();
            this.linkLabel1.LinkClicked += (sender, e) => CancelEdit();
        }

        public DemographicsControl(OpenHRPatient patient, Action<OpenHRPatient> demographicsUpdatedCallback) : this()
        {
            _patient = patient;
            _demographicsUpdatedCallback = demographicsUpdatedCallback;
            
            PopulateDemographics();
        }

        private void PopulateDemographics()
        {
            this.tbTitle.Text = _patient.Person.title;
            this.tbForenames.Text = _patient.Person.forenames;
            this.tbSurname.Text = _patient.Person.surname;
            this.tbSex.Text = _patient.Person.sex.GetSexString();
            this.tbDateOfBirth.Text = _patient.Person.GetCuiDobString();
            this.tbNhsNumber.Text = _patient.Patient.patientIdentifier.GetFormattedNhsNumber();
            this.tbHomeAddress.Text = _patient.Person.address.GetHomeAddress().GetAddressAsMultilineString();
            this.tbHomePhone.Text = _patient.Person.contact.GetFormattedContactValue(vocContactType.H);
            this.tbWorkPhone.Text = _patient.Person.contact.GetFormattedContactValue(vocContactType.W);
            this.tbMobilePhone.Text = _patient.Person.contact.GetFormattedContactValue(vocContactType.M);
            this.tbEmailAddress.Text = _patient.Person.contact.GetFormattedContactValue(vocContactType.EM);
            this.tbPatientGuid.Text = _patient.Patient.id;
        }

        private void CollectDemographics()
        {
            _patient.Person.title = this.tbTitle.Text;
            _patient.Person.forenames = this.tbForenames.Text;
            _patient.Person.surname = this.tbSurname.Text;
            _patient.Person.sex = OpenHRHelperMethods.GetSexFromString(this.tbSex.Text);
            _patient.Person.birthDate = OpenHRHelperMethods.GetDobFromCuiString(this.tbDateOfBirth.Text);
            _patient.Patient.patientIdentifier.UpdateNhsNumber(this.tbNhsNumber.Text.Replace(" ", string.Empty));
            _patient.Person.address.SetHomeAddress(this.tbHomeAddress.Text.Split(new string[] { Environment.NewLine }, StringSplitOptions.RemoveEmptyEntries));
            _patient.Person.SetContactValue(vocContactType.H, tbHomePhone.Text.Replace(" ", string.Empty));
            _patient.Person.SetContactValue(vocContactType.W, tbWorkPhone.Text.Replace(" ", string.Empty));
            _patient.Person.SetContactValue(vocContactType.M, tbMobilePhone.Text.Replace(" ", string.Empty));
            _patient.Person.SetContactValue(vocContactType.EM, tbEmailAddress.Text.Replace(" ", string.Empty));
        }

        private void EditDemographics()
        {
            ToggleEditMode(true);
        }

        private void SaveDemographics()
        {
            ToggleEditMode(false);
            CollectDemographics();
            PopulateDemographics();

            DataStore.SaveOpenHRPatient(_patient);

            if (_demographicsUpdatedCallback != null)
                _demographicsUpdatedCallback(_patient);
        }

        private void CancelEdit()
        {
            ToggleEditMode(false);
            PopulateDemographics();
        }

        private void ToggleEditMode(bool isEditMode)
        {
            ChangeTextBoxPropertiesRecursive(this, isEditMode ? BorderStyle.Fixed3D : BorderStyle.None, (!isEditMode));
            llEditDemographics.Visible = (!isEditMode);
            llSave.Visible = isEditMode;
            linkLabel1.Visible = isEditMode;

            if (isEditMode)
                tbTitle.Select();
            else
                tbTitle.SelectionLength = 0;

            tbPatientGuid.ReadOnly = true;
            
        }

        private void ChangeTextBoxPropertiesRecursive(Control baseControl, BorderStyle borderStyle, bool readOnly)
        {
            TextBox textBox = (baseControl as TextBox);
            
            if (textBox != null)
            {
                textBox.BorderStyle = borderStyle;
                textBox.ReadOnly = readOnly;
            }

            foreach (Control control in baseControl.Controls)
                ChangeTextBoxPropertiesRecursive(control, borderStyle, readOnly);
        }
    }
}
