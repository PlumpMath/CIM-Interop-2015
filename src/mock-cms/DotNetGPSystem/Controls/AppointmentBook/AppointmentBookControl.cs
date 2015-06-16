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

            tableLayoutPanel1.ColumnCount++;
            tableLayoutPanel1.ColumnStyles.Add(new ColumnStyle(SizeType.Absolute, 50F));

            columnCount++;

            foreach (var grouping in users.GroupBy(t => t.Organisation))
            {
                OpenHR001Organisation organisation = grouping.Key;

                foreach (OpenHRUser user in grouping)
                {
                    tableLayoutPanel1.ColumnCount++;
                    tableLayoutPanel1.ColumnStyles.Add(new ColumnStyle(SizeType.Absolute, 200F));

                    Panel panel = new Panel()
                    {
                        Dock = DockStyle.Fill,
                        BackColor = Color.AliceBlue
                    };

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
                        Text = user.Person.GetCuiDisplayName()
                    };

                    Label organisationLabel = new Label()
                    {
                        Font = new Font(this.Font, FontStyle.Bold),
                        Dock = DockStyle.Top,
                        Text = " at " + organisation.name
                    };

                    headerPanel.Controls.Add(organisationLabel);
                    headerPanel.Controls.Add(clinicianLabel);

                    panel.Controls.Add(headerPanel);

                    tableLayoutPanel1.Controls.Add(panel, columnCount++, 0);
                }
            }

            tableLayoutPanel1.ColumnCount++;
            tableLayoutPanel1.ColumnStyles.Add(new ColumnStyle(SizeType.Absolute, 10F));
            panel1.SendToBack();
        }
    }
}
