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

        private static String generateHash(String method, String body)
        {
            ASCIIEncoding encoder = new ASCIIEncoding();

            String privateKey = "privateKey";

            String data = method;
            if (data != null)
                data += body;

            HMAC mac = HMAC.Create("HmacSHA256");
            mac.Key = encoder.GetBytes(privateKey);
            mac.Initialize();
            byte[] digest = mac.ComputeHash(encoder.GetBytes(data));
            String hash = Convert.ToBase64String(digest);
            return hash;
        }

        private void btnGetDemographics_Click(object sender, EventArgs e)
        {
            this.tbGetDemographicsResult.Text = MakeWebRequest(
                relativeUrl: string.Format(this.lblGetDemographicsFhirUrl.Text, this.tbNhsNumber.Text),
                hash: "GalgzDhYBRT0GmsWFtEUEU4dRyL+x0YKmt92i/uyESc=");
        }

        private void btnGetFullRecord_Click(object sender, EventArgs e)
        {
            this.tbGetFullRecordResponse.Text = MakeWebRequest(
               relativeUrl: string.Format(this.lblGetFullRecordFhirUrl.Text, this.tbPatientGuid.Text),
               hash: "RPuOVmDlyaVoBPVWFLBpeyVORZYR6rhAxyeEVbmjWgc=");
        }

        private string MakeWebRequest(string relativeUrl, string hash)
        {
            string url = tbCimUrl.Text + relativeUrl;

            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create(url);
            webRequest.Method = "GET";
            webRequest.Headers.Add("api_key", "swagger");
            webRequest.Headers.Add("hash", hash);

            using (HttpWebResponse webResponse = (HttpWebResponse)webRequest.GetResponse())
                using (Stream stream = webResponse.GetResponseStream())
                    using (StreamReader reader = new StreamReader(stream))
                        return reader.ReadToEnd();
        }
    }
}
