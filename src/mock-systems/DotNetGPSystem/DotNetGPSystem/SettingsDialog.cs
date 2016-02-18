using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DotNetGPSystem
{
    internal partial class SettingsDialog : Form
    {
        public static void ShowSettingsDialog()
        {
            SettingsDialog settingsDialog = new SettingsDialog();

            if (settingsDialog.ShowDialog() == DialogResult.OK)
                DataStore.AutomaticallyFileRecordUpdates = settingsDialog.AutomaticallyFileRecordUpdates;
        }

        public SettingsDialog()
        {
            InitializeComponent();
        }

        private void SettingsDialog_Load(object sender, EventArgs e)
        {
            comboBox1.SelectedIndex = (DataStore.AutomaticallyFileRecordUpdates ? 0 : 1);
        }

        public bool AutomaticallyFileRecordUpdates
        {
            get
            {
                return (comboBox1.SelectedIndex == 0);
            }
        }
    }
}
