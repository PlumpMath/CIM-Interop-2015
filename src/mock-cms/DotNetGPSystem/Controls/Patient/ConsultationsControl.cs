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
    internal partial class ConsultationsControl : UserControl
    {
        private OpenHRPatient _patient;

        private ConsultationsControl()
        {
            InitializeComponent();

            this.Dock = DockStyle.Fill;
        }

        public ConsultationsControl(OpenHRPatient patient) : this()
        {
            _patient = patient;
        }
    }
}
