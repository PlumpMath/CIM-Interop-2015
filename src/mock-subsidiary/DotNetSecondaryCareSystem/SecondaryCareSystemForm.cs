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
            string odsCode = this.tbOdsCode.Text;
            string nhsNumber = this.tbNhsNumber.Text;

            WebResponse response = Utilities.GetCimUrl(
                baseUrl: tbBaseUrl.Text,
                relativeUrl: Utilities.FormatRestPath(restPath, odsCode, nhsNumber),
                apiKey: tbApiKey.Text,
                hash: Utilities.GenerateHash(this.lblGetDemographicsFhirUrl.Text, null));

            this.tbGetDemographicsResult.Text = FormatWebRequestResponse(response);
        }

        private void btnGetFullRecord_Click(object sender, EventArgs e)
        {
            string baseUrl = this.tbBaseUrl.Text;
            string restPath = this.lblGetFullRecordFhirUrl.Text;
            string odsCode = this.tbOdsCode.Text;
            string patientGuid = this.tbPatientGuid.Text;

            WebResponse response = Utilities.GetCimUrl(
               baseUrl: baseUrl,
               relativeUrl: Utilities.FormatRestPath(restPath, odsCode, patientGuid),
               apiKey: tbApiKey.Text,
               hash: Utilities.GenerateHash(restPath, string.Empty));

            this.tbGetFullRecordResponse.Text = FormatWebRequestResponse(response);
        }

        private void btnPostCondition_Click(object sender, EventArgs e)
        {
            string baseUrl = this.tbBaseUrl.Text;
            string restPath = this.lblAddConditionFhirUrl.Text;
            string odsCode = this.tbOdsCode.Text;
            string patientGuid = this.tbAddConditionPatientGuid.Text;
            string payload = this.tbAddConditionPayload.Text;

            WebResponse response = Utilities.PostCimUrl(
               baseUrl: baseUrl,
               relativeUrl: Utilities.FormatRestPath(restPath, odsCode, patientGuid),
               apiKey: tbApiKey.Text,
               hash: Utilities.GenerateHash(restPath, payload),
               payload: payload);

            this.tbAddConditionResponse.Text = FormatWebRequestResponse(response);
        }

        private void btnPutSubscription_Click(object sender, EventArgs e)
        {
            string baseUrl = this.tbBaseUrl.Text;
            string restPath = this.lblSubscribeFhirUrl.Text;
            string odsCode = this.tbOdsCode.Text;
            string patientGuid = this.tbSubscriptionGuid.Text;
            string payload = this.tbSubscriptionPayload.Text;

            WebResponse response = Utilities.PutCimUrl(
               baseUrl: baseUrl,
               relativeUrl: Utilities.FormatRestPath(restPath, odsCode, patientGuid),
               apiKey: tbApiKey.Text,
               hash: Utilities.GenerateHash(restPath, payload),
               payload: payload);

            this.tbSubscriptionResponse.Text = FormatWebRequestResponse(response);
        }

        private static string FormatWebRequestResponse(WebResponse webResponse)
        {
            string result = ((int)webResponse.StatusCode).ToString() + " " + webResponse.StatusCode.ToString() + Environment.NewLine + Environment.NewLine;
            
            if ((webResponse.StatusCode == HttpStatusCode.OK) && (!string.IsNullOrEmpty(webResponse.Response)))
                result += Utilities.FormatJson(webResponse.Response);
            else
                result += webResponse.Response;

            return result;
        }

        
    }
}
