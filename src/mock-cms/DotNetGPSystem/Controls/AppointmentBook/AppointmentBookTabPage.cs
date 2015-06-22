using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DotNetGPSystem
{
    internal class AppointmentBookTabPage : TabPage
    {
        private AppointmentBookControl _appointmentBook;
        
        public AppointmentBookTabPage()
        {
            this.Text = "Appointment Book";

            _appointmentBook = new AppointmentBookControl(DataStore.Organisations);
            _appointmentBook.Dock = DockStyle.Fill;
            _appointmentBook.Parent = this;
        }
    }
}
