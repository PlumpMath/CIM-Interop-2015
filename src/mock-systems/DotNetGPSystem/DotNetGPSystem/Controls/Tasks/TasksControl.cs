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
    internal partial class TasksControl : UserControl
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

            Task task = new Task()
            {
                OpenHRXml = openHRXml,
                DateStamp = dateStamp,
                CanFile = false
            };

            AddTask(task);
        }

        public void AddTask(Task task)
        {
            string taskName = "External update received";
            string patientName = string.Empty;
            string description = string.Empty;
            Bitmap taskImage = DotNetGPSystem.Properties.Resources.email;

            try
            {
                task.Display = "Update message:" + Environment.NewLine + Environment.NewLine + Utilities.FormatXml(task.OpenHRXml);

                OpenHR001OpenHealthRecord openHR = Utilities.Deserialize<OpenHR001OpenHealthRecord>(task.OpenHRXml);

                task.Event = openHR.healthDomain.@event.FirstOrDefault();

                task.Patient = DataStore.OpenHRPatients.FirstOrDefault(t => new Guid(t.Patient.id) == new Guid(task.Event.patient));

                if (task.Patient != null)
                {
                    patientName = task.Patient.Person.GetCuiDisplayName();

                    description = "Add event '" + (task.Event.code.WhenNotNull(t => t.displayName) ?? "(no code found)") + "'";

                    task.CanFile = true;
                }
                else
                {
                    patientName = "(no patient identified)";
                }
            }
            catch (Exception e)
            {
                patientName = "(no patient identified)";
                task.Display = "Error occurred parsing openHR XML: " + e.Message + Environment.NewLine + Environment.NewLine + task.Display;
                taskImage = DotNetGPSystem.Properties.Resources.email_error;
            }
            
            DataGridViewRow row = (DataGridViewRow)dataGridView.RowTemplate.Clone();
            row.CreateCells(dataGridView);

            row.SetValues(taskImage, task.DateStamp.ToString("dd-MMM-yyyy HH:mm:ss"), patientName, taskName, description);

            row.Tag = task;

            dataGridView.Rows.Add(row);
        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            textBox1.Clear();

            btnFile.Enabled = false;
            lblFiled.Visible = false;
            bool taskSelected = (dataGridView.SelectedRows.Count > 0);

            if (taskSelected)
            {
                Task task = (Task)dataGridView.SelectedRows.Cast<DataGridViewRow>().First().Tag;
                textBox1.Text = task.Display;
                btnFile.Enabled = task.CanFile;
                lblFiled.Visible = task.Filed;
            }
        }

        private void btnFile_Click(object sender, EventArgs e)
        {
            Task task = (Task)dataGridView.SelectedRows.Cast<DataGridViewRow>().First().Tag;
            task.CanFile = false;
            btnFile.Enabled = false;
            task.Filed = true;

            List<OpenHR001HealthDomainEvent> events = new List<OpenHR001HealthDomainEvent>();
            events.Add(task.Event);
            events.AddRange(task.Patient.OpenHealthRecord.healthDomain.@event);
            task.Patient.OpenHealthRecord.healthDomain.@event = events.ToArray();

            lblFiled.Visible = task.Filed;
        }
    }
}
