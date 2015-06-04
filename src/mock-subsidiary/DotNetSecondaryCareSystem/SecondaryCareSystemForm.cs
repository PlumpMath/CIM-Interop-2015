using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
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

        private void button1_Click(object sender, EventArgs e)
        {
            tbCimUrl.Text + 

            var webRequest = (HttpWebRequest)WebRequest.Create("http://endeavour-cim.cloudapp.net/CIM_war/v0.1/service/21/patient/5ec13d3e-cd1c-42b0-9203-fac7d7440c70");
            webRequest.Method = "GET";
            webRequest.Headers.Add("api_key", "swagger");
            webRequest.Headers.Add("hash", generateHash("/service/{serviceId}/patient/{patientId}", null));

            var webResponse = (HttpWebResponse)webRequest.GetResponse();

            Stream responseStream = webResponse.GetResponseStream();
            String response;
            using (StreamReader reader = new StreamReader(responseStream))
            {
                response = reader.ReadToEnd();
            }
            Debug.WriteLine(response);
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
    }
}
