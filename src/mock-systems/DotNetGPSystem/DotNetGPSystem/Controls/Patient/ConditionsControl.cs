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
    internal partial class ConditionsControl : UserControl
    {
        private OpenHRPatient _patient;
        private List<DataGridViewRow> _headerRows = new List<DataGridViewRow>();

        private ConditionsControl()
        {
            InitializeComponent();

            this.Dock = DockStyle.Fill;
        }

        public ConditionsControl(OpenHRPatient patient) : this()
        {
            _patient = patient;

            PopulateConditions();
        }

        private void PopulateConditions()
        {
            OpenHR001Problem[] problems = _patient.OpenHealthRecord.healthDomain.problem ?? new OpenHR001Problem[] { };
            
            PopulateConditionGroup(vocProblemStatus.A.GetDescription(), problems.Where(t => t.status == vocProblemStatus.A).ToArray());
            PopulateConditionGroup(vocProblemStatus.I.GetDescription(), problems.Where(t => t.status == vocProblemStatus.I).ToArray());
            PopulateConditionGroup(vocProblemStatus.HP.GetDescription(), problems.Where(t => t.status == vocProblemStatus.HP).ToArray());
            PopulateConditionGroup(vocProblemStatus.PP.GetDescription(), problems.Where(t => t.status == vocProblemStatus.PP).ToArray());
        }

        private void PopulateConditionGroup(string description, OpenHR001Problem[] problems2)
        {
            DataGridViewRow headerRow = dataGridView.CloneDataGridViewRow(FontStyle.Bold, Color.AliceBlue);
            _headerRows.Add(headerRow);
            headerRow.SetValues(description);
            dataGridView.Rows.Add(headerRow);

            if (problems2.Length == 0)
            {
                DataGridViewRow row = dataGridView.CloneDataGridViewRow(FontStyle.Italic);
                row.SetValues("No conditions");
                dataGridView.Rows.Add(row);
            }
            else
            {
                var problems = problems2
                    .Select(t =>
                        new
                        {
                            Problem = t,
                            Event = _patient.HealthDomainEvents.FirstOrDefault(s => s.id == t.id)
                        });

                foreach (var problem in problems.OrderByDescending(t => t.Event.effectiveTime.value))
                {
                    OpenHR001HealthDomainEvent healthEvent = problem.Event;

                    string eventType = problem.Problem.significance.GetProblemSignficance();
                    string effectiveTime = healthEvent.effectiveTime.GetFormattedDate();
                    string displayTerm = healthEvent.displayTerm;
                    string code = healthEvent.code.WhenNotNull(t => t.code);
                    string text = healthEvent.GetAssociatedTextWithValue();

                    DataGridViewRow row = dataGridView.CloneDataGridViewRow();
                    row.SetValues(eventType, effectiveTime, code, displayTerm, text);
                    dataGridView.Rows.Add(row);
                }
            }
        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            DataGridViewRow row = dataGridView.SelectedRows.Cast<DataGridViewRow>().FirstOrDefault();

            if (_headerRows.Contains(row))
                row.Selected = false;
        }
    }
}
