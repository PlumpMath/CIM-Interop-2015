﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
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
            this.tbNhsNumber.Text = this.tbNhsNumber.Text.Replace(" ", string.Empty);
            this.tbNhsNumber.Refresh();

            string baseUrl = this.tbBaseUrl.Text;
            string odsCode = this.tbOdsCode.Text;
            string nhsNumber = this.tbNhsNumber.Text;
            string relativeUrl = Utilities.FormatRestPath(this.lblGetDemographicsFhirUrl.Text, odsCode, nhsNumber);

            WebResponse response = Utilities.GetCimUrl(
                baseUrl: tbBaseUrl.Text,
                relativeUrl: relativeUrl,
                apiKey: tbApiKey.Text,
                hash: Utilities.GenerateHash(relativeUrl, string.Empty, tbSecret.Text));

            this.tbGetDemographicsResult.Text = FormatWebRequestResponse(response);
        }

        private void btnGetFullRecord_Click(object sender, EventArgs e)
        {
            string odsCode = this.tbOdsCode.Text;
            string patientGuid = this.tbPatientGuid.Text;

            this.tbGetFullRecordResponse.Text = FormatWebRequestResponse(GetFullRecord(odsCode, patientGuid));
        }

        private void btnGetAndSaveFullRecords_Click(object sender, EventArgs e)
        {
            foreach (string line in tbPatientGuids.Lines)
            {
                if (string.IsNullOrEmpty(line))
                    continue;

                string odsCode = line.Split(',').First().Trim();
                string patientGuid = line.Split(',').Skip(1).First().Trim();

                string fullRecord = Utilities.FormatXml(GetFullRecord(odsCode, patientGuid).Response);

                File.WriteAllText(Path.Combine(tbOutputFolder.Text, patientGuid + ".fhir.xml"), fullRecord);
            }
        }

        private WebResponse GetFullRecord(string odsCode, string patientGuid)
        {
            string baseUrl = this.tbBaseUrl.Text;
            string relativeUrl = Utilities.FormatRestPath(this.lblGetFullRecordFhirUrl.Text, odsCode, patientGuid);

            WebResponse response = Utilities.GetCimUrl(
               baseUrl: baseUrl,
               relativeUrl: relativeUrl,
               apiKey: tbApiKey.Text,
               hash: Utilities.GenerateHash(relativeUrl, string.Empty, tbSecret.Text));

            return response;
        }

        private void btnPostCondition_Click(object sender, EventArgs e)
        {
            string baseUrl = this.tbBaseUrl.Text;
            string restPath = this.lblAddConditionFhirUrl.Text;
            string odsCode = this.tbOdsCode.Text;
            string patientGuid = this.tbAddConditionPatientGuid.Text;
            string payload = this.tbAddConditionPayload.Text.Replace("<PATIENT_GUID>", patientGuid);

            WebResponse response = Utilities.PostCimUrl(
               baseUrl: baseUrl,
               relativeUrl: Utilities.FormatRestPath(restPath, odsCode, patientGuid),
               apiKey: tbApiKey.Text,
               hash: Utilities.GenerateHash(restPath, payload, tbSecret.Text),
               payload: payload);

            this.tbAddConditionResponse.Text = FormatWebRequestResponse(response);
        }

        private void btnPutSubscription_Click(object sender, EventArgs e)
        {
            this.tbSubscriptionNhsNumber.Text = this.tbSubscriptionNhsNumber.Text.Replace(" ", string.Empty);
            this.tbSubscriptionNhsNumber.Refresh();

            string baseUrl = this.tbBaseUrl.Text;
            string restPath = this.lblSubscribeFhirUrl.Text;
            string odsCode = this.tbOdsCode.Text;
            string patientGuid = this.tbSubscriptionGuid.Text;
            string payload = this.tbSubscriptionPayload.Text.Replace("<NHS_NUMBER>", tbSubscriptionNhsNumber.Text).Replace("<SUBSCRIPTION_GUID>", tbSubscriptionGuid.Text);

            WebResponse response = Utilities.PutCimUrl(
               baseUrl: baseUrl,
               relativeUrl: Utilities.FormatRestPath(restPath, odsCode, patientGuid),
               apiKey: tbApiKey.Text,
               hash: Utilities.GenerateHash(restPath, payload, tbSecret.Text),
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

        private void StartApiService()
        {
            try
            {
                WebServer webServer = new WebServer("http://localhost:9002/SecondaryCareApiService/", HandleInboundRequest);
                webServer.Run();

                lblServiceStatus.Text = "RUNNING";
                lblServiceStatus.ForeColor = Color.Green;
            }
            catch (Exception exception)
            {
                lblServiceStatus.Text = "NOT STARTED";
                lblServiceStatus.ForeColor = Color.Red;
            }
        }

        private string HandleInboundRequest(HttpListenerRequest request)
        {
            if (request.HttpMethod == "GET")
                return "<html><head><title></title><body>Secondary Care API Service</body></html>";


            string httpHeadersAndPath = request.HttpMethod + " " + request.RawUrl + " " + request.ProtocolVersion;
            
            string postData = string.Empty;
            
            if (request.InputStream != null)
                using (System.IO.Stream body = request.InputStream)
                    using (System.IO.StreamReader reader = new System.IO.StreamReader(body, request.ContentEncoding))
                        postData = reader.ReadToEnd();

            
            LogMessage(httpHeadersAndPath, postData);

            return string.Empty;
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            this.timer.Enabled = false;

            StartApiService();
        }

        private void llServiceStatus_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            Process.Start(llServiceStatus.Text);
        }

        public void LogMessage(string httpHeaderAndPath, string message)
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new Action<string, string>(LogMessage), new object[] { httpHeaderAndPath, message });
                return;
            }
            
            DataGridViewRow row = (DataGridViewRow)dataGridView.RowTemplate.Clone();
            row.CreateCells(dataGridView);

            row.SetValues(DateTime.Now.ToString("yyyy-MMM-dd HH:mm:ss"), httpHeaderAndPath);

            row.Tag = Utilities.FormatJson(message);

            dataGridView.Rows.Add(row);
        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            this.textBox1.Clear();

            if (this.dataGridView.SelectedRows.Count > 0)
            {
                DataGridViewRow row = this.dataGridView.SelectedRows.Cast<DataGridViewRow>().First();

                this.textBox1.Text = row.Tag.ToString();
            }
        }
    }
}
