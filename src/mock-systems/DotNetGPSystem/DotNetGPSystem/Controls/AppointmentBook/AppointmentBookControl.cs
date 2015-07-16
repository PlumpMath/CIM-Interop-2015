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
    internal partial class AppointmentBookControl : UserControl
    {
        private OpenHROrganisation[] _organisations;
        private OpenHRUser[] _allUsers;
        private OpenHRUser[] _distinctUsers;

        private ContextMenuStrip _contextMenuStrip;
        
        private AppointmentBookControl()
        {
            InitializeComponent();

            DataStore.ExternalAppointmentBookChangeMade += DataStore_ExternalAppointmentBookChangeMade;
        }

        private void DataStore_ExternalAppointmentBookChangeMade(object sender, EventArgs e)
        {
            if (this.InvokeRequired)
            {
                this.Invoke((EventHandler)DataStore_ExternalAppointmentBookChangeMade, new object[] { sender, e });
                return;
            }

            DrawAppointmentBook();
        }

        public AppointmentBookControl(OpenHROrganisation[] organisations) : this()
        {
            _organisations = organisations;

            _allUsers = organisations
                .SelectMany(t => t.Users)
                .ToArray();
            
            _distinctUsers = _allUsers
                .DistinctBy(t => new Guid(t.Person.id))
                .ToArray();

            cbOrganisationFilter.PopulateComboBox(organisations, t => t.Organisation.name, "(no filter)");

            monthCalendar1.BoldedDates = DataStore
                .AppointmentSessions
                .Select(t => t.Date.Date)
                .Distinct()
                .ToArray();
        }

        private void cbOrganisationFilter_SelectedIndexChanged(object sender, EventArgs e)
        {
            OpenHROrganisation selectedOrganisation = (OpenHROrganisation)cbOrganisationFilter.SelectedValue;
            OpenHRUser selectedUser = (OpenHRUser)cbClinicianFilter.SelectedValue;

            OpenHRUser[] users = _distinctUsers.Where(t => t.IsSessionHolder).ToArray();

            if (selectedOrganisation != null)
                users = selectedOrganisation.Users;

            cbClinicianFilter.PopulateComboBox(users.OrderBy(t => t.Person.GetCuiDisplayName()).ToArray(), t => t.Person.GetCuiDisplayName(), "(no filter)");

            if (selectedUser != null)
            {
                cbClinicianFilter.SelectedValue = selectedUser;

                if (cbClinicianFilter.SelectedValue == null)
                    cbClinicianFilter.SelectedIndex = 0;
            }
        }

        private void cbClinicianFilter_SelectedIndexChanged(object sender, EventArgs e)
        {
            DrawAppointmentBook();
        }

        private void DrawAppointmentBook()
        {
            lblDateHeader.Text = monthCalendar1.SelectionStart.Date.ToString("dddd dd MMMM yyyy");
            
            OpenHRUser[] userFilter = _allUsers;

            if (cbClinicianFilter.SelectedValue != null)
            {
                OpenHRUser selectedUser = (OpenHRUser)cbClinicianFilter.SelectedValue;

                userFilter = _allUsers
                    .Where(t => new Guid(t.Person.id) == new Guid(selectedUser.Person.id))
                    .ToArray();
            }

            if (cbOrganisationFilter.SelectedValue != null)
            {
                OpenHROrganisation selectedOrganisation = (OpenHROrganisation)cbOrganisationFilter.SelectedValue;

                userFilter = userFilter
                    .Where(t => new Guid(t.Organisation.id) == new Guid(selectedOrganisation.Organisation.id))
                    .ToArray();
            }

            DateTime selectedDate = monthCalendar1.SelectionStart.Date;

            Session[] sessions = DataStore
                    .AppointmentSessions
                    .Where(t => t.Date.Date.Equals(selectedDate)
                        && userFilter.Contains(t.User))
                    .OrderBy(t => t.Date)
                    .ThenBy(t => t.Organisation.Organisation.name)
                    .ThenBy(t => t.User.Person.GetCuiDisplayName())
                    .ToArray();

            DrawAppointmentBook(sessions);
        }

        private void ClearAndDisposeControls(ControlCollection controls)
        {
            for (int i = 0; i < controls.Count; i++)
            {
                Control control = controls[i];

                if (control.Controls != null)
                    if (control.Controls.Count > 0)
                        ClearAndDisposeControls(control.Controls);

                if (control.ContextMenuStrip != null)
                    control.ContextMenuStrip = null;

                control.Dispose();
                control = null;
            }

            controls.Clear();
        }

        private ContextMenuStrip CreateContextMenuStrip()
        {
            ContextMenuStrip contextMenuStrip1 = new ContextMenuStrip();
            ToolStripMenuItem btnBookSlot = new ToolStripMenuItem();
            ToolStripMenuItem btnCancelSlot = new ToolStripMenuItem();

            contextMenuStrip1.SuspendLayout();

            contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] { btnBookSlot, btnCancelSlot});
            contextMenuStrip1.Name = "contextMenuStrip1";
            contextMenuStrip1.Size = new System.Drawing.Size(133, 48);
            btnBookSlot.Name = "toolStripMenuItem1";
            btnBookSlot.Size = new System.Drawing.Size(132, 22);
            btnBookSlot.Text = "Book slot";
            btnBookSlot.Click += new System.EventHandler(btnBookSlot_Click);
            btnCancelSlot.Name = "toolStripMenuItem2";
            btnCancelSlot.Size = new System.Drawing.Size(132, 22);
            btnCancelSlot.Text = "Cancel slot";
            btnCancelSlot.Click += new System.EventHandler(btnCancelSlot_Click);

            contextMenuStrip1.ResumeLayout(false);

            return contextMenuStrip1;
        }

        private void DrawAppointmentBook(Session[] sessions)
        {
            panel1.BringToFront();
            panel1.Refresh();

            tableLayoutPanel1.SuspendLayout();
            
            ClearAndDisposeControls(tableLayoutPanel1.Controls);

            if (_contextMenuStrip != null)
            {
                _contextMenuStrip.Dispose();
                _contextMenuStrip = null;
            }

            _contextMenuStrip = CreateContextMenuStrip();

            tableLayoutPanel1.ColumnStyles.Clear();
            tableLayoutPanel1.ColumnCount = 0;

            int columnCount = 0;

            AddVerticalTimeStripPanel();
            columnCount++;

            foreach (Session session in sessions)
            {
                AddVerticalUserPanel(session);
                columnCount++;

                if (columnCount % 5 == 0)
                {
                    AddVerticalTimeStripPanel();
                    columnCount++;
                }
            }

            tableLayoutPanel1.CreateColumn(new ColumnStyle(SizeType.Absolute, 10F));

            tableLayoutPanel1.ResumeLayout();

            panel1.SendToBack();
        }

        private void AddVerticalUserPanel(Session session)
        {
            OpenHRUser user = session.User;
            OpenHR001Organisation organisation = session.Organisation.Organisation;

            tableLayoutPanel1.CreateColumn(new ColumnStyle(SizeType.Absolute, 200F));
            Panel panel = CreateVerticalPanel(user.Person.GetCuiDisplayName(), " at " + organisation.name);

            foreach (Slot slot in session.Slots)
            {
                Panel outer = new Panel()
                {
                    Height = 100,
                    Dock = DockStyle.Top,
                    Padding = new Padding(10, 10, 10, 0)
                };
                
                Panel p = new Panel()
                {
                    Dock = DockStyle.Fill,
                    BackColor = Color.White, 
                    ContextMenuStrip = _contextMenuStrip,
                    Tag = slot
                };
                
                if (slot.Patient != null)
                {
                    Control c = CreateBookedPatientControl(slot.Patient);
                    p.Controls.Add(c);
                }

                outer.Controls.Add(p);
                panel.Controls.Add(outer);
                outer.BringToFront();
            }

            tableLayoutPanel1.Controls.Add(panel, (tableLayoutPanel1.ColumnCount - 1), 0);
        }

        private void AddVerticalTimeStripPanel()
        {
            tableLayoutPanel1.CreateColumn(new ColumnStyle(SizeType.Absolute, 50F));
            Panel timeStripPanel = CreateVerticalTimeStrip();
            tableLayoutPanel1.Controls.Add(timeStripPanel, (tableLayoutPanel1.ColumnCount - 1), 0);
        }

        private Panel CreateVerticalTimeStrip()
        {
            Panel timePanel = CreateVerticalPanel(string.Empty, string.Empty);

            foreach (int time in DataStore.AppointmentTimes)
            {
                Panel p = new Panel()
                {
                    Height = 100,
                    Dock = DockStyle.Top,
                    BackColor = Color.WhiteSmoke
                };

                Label timeLabel = new Label()
                {
                    Font = new Font(this.Font, FontStyle.Regular),
                    Text = time.ToString().PadLeft(2, '0') + ":00",
                    Dock = DockStyle.Top,
                    Padding = new Padding(5, 2, 0, 0)
                };

                p.Controls.Add(timeLabel);

                timePanel.Controls.Add(p);
                p.BringToFront();
            }

            return timePanel;
        }

        private Panel CreateVerticalPanel(string headerLine1, string headerLine2)
        {
            Panel panel = new Panel()
            {
                Dock = DockStyle.Fill,
                BackColor = Color.AliceBlue
            };

            Panel headerPanel = CreateHeaderPanel(headerLine1, headerLine2);
            panel.Controls.Add(headerPanel);

            return panel;
        }

        private Panel CreateHeaderPanel(string line1, string line2)
        {
            Panel headerPanel = new Panel()
            {
                AutoSize = true,
                Dock = DockStyle.Top,
                BackColor = Color.White
            };

            Label clinicianLabel = new Label()
            {
                Font = new Font(this.Font, FontStyle.Bold),
                Dock = DockStyle.Top,
                Text = line1
            };

            Label organisationLabel = new Label()
            {
                Font = new Font(this.Font, FontStyle.Bold),
                Dock = DockStyle.Top,
                Text = line2
            };

            headerPanel.Controls.Add(organisationLabel);
            headerPanel.Controls.Add(clinicianLabel);

            return headerPanel;
        }

        private void btnBookSlot_Click(object sender, EventArgs e)
        {
            OpenHRPatient patient = PatientFindForm.ChoosePatient();

            if (patient != null)
            {
                Control control = ((sender as ToolStripMenuItem)
                    .WhenNotNull(t => (t.Owner as ContextMenuStrip).WhenNotNull(s => s.SourceControl)));

                if (control != null)
                {
                    ClearAndDisposeControls(control.Controls);

                    Control l = CreateBookedPatientControl(patient);
                    
                    Slot slot = control.Tag as Slot;
                    slot.Patient = patient;

                    control.Controls.Add(l);
                }
            }
        }

        private Control CreateBookedPatientControl(OpenHRPatient patient)
        {
            Label l = new Label()
            {
                BackColor = Color.Silver,
                ForeColor = Color.ForestGreen,
                Dock = DockStyle.Fill,
                TextAlign = ContentAlignment.MiddleCenter,
                Font = new Font(this.Font.FontFamily, this.Font.Size + 1, FontStyle.Bold),
                Text = patient.Person.GetCuiDisplayName() + "\r\n" + patient.Person.GetCuiDobStringWithAge()
            };

            return l;
        }

        private void btnCancelSlot_Click(object sender, EventArgs e)
        {
            Control control = ((sender as ToolStripMenuItem)
                    .WhenNotNull(t => (t.Owner as ContextMenuStrip).WhenNotNull(s => s.SourceControl)));

            if (control != null)
                ClearAndDisposeControls(control.Controls);

            Slot slot = control.Tag as Slot;

            if (slot != null)
                slot.Patient = null;
        }

        private void monthCalendar1_DateSelected(object sender, DateRangeEventArgs e)
        {
            DrawAppointmentBook();
        }
    }
}
