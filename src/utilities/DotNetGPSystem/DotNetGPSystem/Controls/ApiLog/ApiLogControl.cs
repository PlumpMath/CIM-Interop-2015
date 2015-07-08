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
    internal partial class ApiLogControl : UserControl
    {
        public ApiLogControl()
        {
            InitializeComponent();
        }

        public void LogMessage(ApiLogMessage message)
        {
            DataGridViewRow row = (DataGridViewRow)dataGridView.RowTemplate.Clone();
            row.CreateCells(dataGridView);

            row.SetValues(message.DateStamp.ToString("yyyy-MMM-dd HH:mm:ss"), message.HttpMethodAndPath);

            row.Tag = message;

            dataGridView.Rows.Add(row);
        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            this.textBox1.Clear();
            this.textBox2.Clear();

            if (this.dataGridView.SelectedRows.Count > 0)
            {
                DataGridViewRow row = this.dataGridView.SelectedRows.Cast<DataGridViewRow>().First();

                this.textBox1.Text = ((ApiLogMessage)row.Tag).RequestMessage;
                this.textBox2.Text = ((ApiLogMessage)row.Tag).ResponseMessage;
            }
        }
    }
}
