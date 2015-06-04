using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DotNetSecondaryCareSystem
{
    public partial class SecondaryCareSystemForm : Form
    {
        public SecondaryCareSystemForm()
        {
            InitializeComponent();
        }

        private void btnGetDemographics_Click(object sender, EventArgs e)
        {
            string baseUrl = this.tbBaseUrl.Text;
            string restPath = this.lblGetDemographicsFhirUrl.Text;
            string odsCode = this.tbGetDemographicsOdsCode.Text;
            string nhsNumber = this.tbNhsNumber.Text;

            WebResponse response = Utilities.MakeWebRequest(
                baseUrl: tbBaseUrl.Text,
                relativeUrl: Utilities.FormatRestPath(restPath, odsCode, nhsNumber),
                hash: Utilities.GenerateHash(this.lblGetDemographicsFhirUrl.Text, null));

            this.tbGetDemographicsResult.Text = FormatWebRequestResponse(response);
        }

        private void btnGetFullRecord_Click(object sender, EventArgs e)
        {
            string baseUrl = this.tbBaseUrl.Text;
            string restPath = this.lblGetFullRecordFhirUrl.Text;
            string odsCode = this.tbFullRecordOdsCode.Text;
            string patientGuid = this.tbPatientGuid.Text;

            WebResponse response = Utilities.MakeWebRequest(
               baseUrl: baseUrl,
               relativeUrl: Utilities.FormatRestPath(restPath, odsCode, patientGuid),
               hash: Utilities.GenerateHash(restPath, string.Empty));

            this.tbGetFullRecordResponse.Text = FormatWebRequestResponse(response);
        }

        private static string FormatWebRequestResponse(WebResponse webResponse)
        {
            if (webResponse.StatusCode == HttpStatusCode.OK)
                return Utilities.FormatJson(webResponse.Response);

            return webResponse.Response;
        }
    }
}
