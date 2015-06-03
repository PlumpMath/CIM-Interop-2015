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

            AddTask();
        }

        public void AddTask()
        {
            DataGridViewRow row = (DataGridViewRow)dataGridView.RowTemplate.Clone();
            row.CreateCells(dataGridView);

            row.SetValues(DotNetGPSystem.Properties.Resources.email, DateTime.Now.ToString("yyyy-MMM-dd HH:mm:ss"), "SMITH, Jones (Mr)", "This is a task to....");

            //row.Tag = message;

            dataGridView.Rows.Add(row);
        }
    }
}
