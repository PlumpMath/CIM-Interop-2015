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
    internal partial class ApiLogTabPage : TabPage
    {
        private ApiLogControl _apiLogControl;
        
        public ApiLogTabPage()
        {
            this.Text = "API Log";
            _apiLogControl = new ApiLogControl();
            _apiLogControl.Dock = DockStyle.Fill;
            _apiLogControl.Parent = this;
        }

        public void LogMessage(ApiLogMessage message)
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new LogMessage(LogMessage), message);
                return;
            }

            _apiLogControl.LogMessage(message);
        }
    }
}
