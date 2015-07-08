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
    internal partial class TasksTabPage : TabPage
    {
        private TasksControl _tasksControl;
        
        public TasksTabPage()
        {
            this.Text = "Tasks";

            _tasksControl = new TasksControl();
            _tasksControl.Dock = DockStyle.Fill;
            _tasksControl.Parent = this;
        }
    }
}
