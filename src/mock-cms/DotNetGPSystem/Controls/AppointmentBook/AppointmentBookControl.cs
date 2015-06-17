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
        private int[] _appointmentTimes = new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 };
        
        private AppointmentBookControl()
        {
            InitializeComponent();
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

            DrawAppointmentBook();
        }

        private void cbOrganisationFilter_SelectedIndexChanged(object sender, EventArgs e)
        {
            OpenHROrganisation selectedOrganisation = (OpenHROrganisation)cbOrganisationFilter.SelectedValue;
            OpenHRUser selectedUser = (OpenHRUser)cbClinicianFilter.SelectedValue;

            OpenHRUser[] users = _distinctUsers;

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

            DrawAppointmentBook(userFilter);
        }

        private void DrawAppointmentBook(OpenHRUser[] users)
        {
            panel1.BringToFront();
            panel1.Refresh();
            tableLayoutPanel1.Controls.Clear();
            tableLayoutPanel1.ColumnStyles.Clear();
            tableLayoutPanel1.ColumnCount = 0;

            int columnCount = 0;

            AddVerticalTimeStripPanel();
            columnCount++;

            foreach (var grouping in users.GroupBy(t => t.Organisation))
            {
                OpenHR001Organisation organisation = grouping.Key;

                foreach (OpenHRUser user in grouping)
                {
                    AddVerticalUserPanel(user, organisation);
                    columnCount++;

                    if (columnCount % 5 == 0)
                    {
                        AddVerticalTimeStripPanel();
                        columnCount++;
                    }
                }
            }

            tableLayoutPanel1.CreateColumn(new ColumnStyle(SizeType.Absolute, 10F));
            panel1.SendToBack();
        }

        private void AddVerticalUserPanel(OpenHRUser user, OpenHR001Organisation organisation)
        {
            tableLayoutPanel1.CreateColumn(new ColumnStyle(SizeType.Absolute, 200F));
            Panel panel = CreateVerticalPanel(user.Person.GetCuiDisplayName(), " at " + organisation.name);

            foreach (int time in _appointmentTimes)
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
                    ContextMenuStrip = contextMenuStrip1
                };

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

            foreach (int time in _appointmentTimes)
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

        private void toolStripMenuItem1_Click(object sender, EventArgs e)
        {
            System.Windows.Forms.MessageBox.Show("Book slot");
        }
    }
}
