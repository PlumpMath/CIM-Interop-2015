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
    internal partial class PatientTabPage : TabPage, IKeyHandler
    {
        private OpenHRPatient _patient;
        private PatientControl _patientControl;
        
        private PatientTabPage()
        {
        }

        public PatientTabPage(OpenHRPatient patient) : this()
        {
            SetPatient(patient);

            _patientControl = new PatientControl(patient, SetPatient);
            _patientControl.Parent = this;
        }

        private void SetPatient(OpenHRPatient patient)
        {
            _patient = patient;

            this.Text = "Patient Record";
        }

        public OpenHRPatient Patient
        {
            get
            {
                return _patient;
            }
        }

        public bool ProcessKey(Keys key)
        {
            return _patientControl.ProcessKey(key);
        }
    }
}
