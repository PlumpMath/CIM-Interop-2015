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
    public partial class TasksControl : UserControl
    {
        public TasksControl()
        {
            InitializeComponent();

            DataStore.ExternalUpdateReceived += DataStore_ExternalUpdateReceived;
        }

        private void DataStore_ExternalUpdateReceived(DateTime dateStamp, string openHRXml)
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new ExternalUpdateReceivedHandler(DataStore_ExternalUpdateReceived), new object[] { dateStamp, openHRXml });
                return;
            }

            AddTask(dateStamp, openHRXml);
        }

        public void AddTask(DateTime dateStamp, string openHRXml)
        {
            string taskName = "External update received";
            string patientName = string.Empty;
            string description = string.Empty;
            Bitmap taskImage = DotNetGPSystem.Properties.Resources.email;

            string taskBody = "Update message:" + Environment.NewLine + Environment.NewLine + Utilities.FormatXml(openHRXml);
            
            try
            {
                OpenHR001OpenHealthRecord openHR = Utilities.Deserialize<OpenHR001OpenHealthRecord>(openHRXml);

                var condition = openHR.healthDomain.@event.FirstOrDefault();
                
                OpenHRPatient patient = DataStore.OpenHRPatients.FirstOrDefault(t => new Guid(t.Patient.id) == new Guid(condition.patient));
                patientName = patient.Person.GetCuiDisplayName();

                description = "Add condition '" + condition.code.displayName + "'";
            }
            catch (Exception e)
            {
                patientName = "(no patient identified)";
                taskBody = "Error occurred parsing openHR XML: " + e.Message + Environment.NewLine + Environment.NewLine + taskBody;
                taskImage = DotNetGPSystem.Properties.Resources.email_error;
            }
            
            DataGridViewRow row = (DataGridViewRow)dataGridView.RowTemplate.Clone();
            row.CreateCells(dataGridView);

            row.SetValues(taskImage, dateStamp.ToString("yyyy-MMM-dd HH:mm:ss"), patientName, taskName, description);

            row.Tag = taskBody;

            dataGridView.Rows.Add(row);
        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            textBox1.Clear();

            if (dataGridView.SelectedRows.Count > 0)
                textBox1.Text = (string)dataGridView.SelectedRows.Cast<DataGridViewRow>().First().Tag;
        }
    }
}
