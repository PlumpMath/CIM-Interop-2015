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
    internal partial class DemographicsPage : UserControl
    {
        private DemographicsPage()
        {
            InitializeComponent();

            this.Dock = DockStyle.Fill;
        }

        public DemographicsPage(OpenHRPatient patient) : this()
        {
            this.tbTitle.Text = patient.Person.title;
            this.tbForenames.Text = patient.Person.forenames;
            this.tbSurname.Text = patient.Person.surname;
            this.tbSex.Text = patient.Person.sex.GetSexString();
            this.tbDateOfBirth.Text = patient.Person.GetCuiDobString();
            this.tbNhsNumber.Text = patient.Patient.patientIdentifier.GetFormattedNhsNumber();
        }

        private void llEditDemographics_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            foreach (Control control in this.tableLayoutPanel1.Controls)
                if (control is TextBox)
                    (control as TextBox).BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
        }
    }
}
