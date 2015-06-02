﻿using System;
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
    internal partial class OpenHRPage : UserControl
    {
        private OpenHRPage()
        {
            InitializeComponent();

            this.Dock = DockStyle.Fill;
        }

        public OpenHRPage(OpenHRPatient patient) : this()
        {
            PopulatePatient(patient);
        }

        public void PopulatePatient(OpenHRPatient patient)
        {
            this.textBox1.Text = Utilities.Serialize<OpenHR001OpenHealthRecord>(patient.OpenHealthRecord);
        }
    }
}
