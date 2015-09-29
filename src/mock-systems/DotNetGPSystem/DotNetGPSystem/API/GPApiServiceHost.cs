using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using System.ServiceModel.Description;
using System.Windows.Forms;

namespace DotNetGPSystem
{
    internal class GPApiServiceHost
    {
        public void StartService(int portNumber, LogMessage logMessage)
        {
            ServiceHost svcHost = new ServiceHost(typeof(GPApiService), new Uri(string.Format("http://localhost:{0}/GPApiService", portNumber)));
            svcHost.AddServiceEndpoint(typeof(IGPApiService), new BasicHttpBinding(), "Soap");

            ServiceMetadataBehavior smb = svcHost.Description.Behaviors.Find<ServiceMetadataBehavior>();

            if (smb == null)
                smb = new ServiceMetadataBehavior();

            smb.HttpGetEnabled = true;
            svcHost.Description.Behaviors.Add(smb);

            svcHost.Description.Behaviors.Add(new GPApiServiceLogger(logMessage));

            svcHost.AddServiceEndpoint(ServiceMetadataBehavior.MexContractName, MetadataExchangeBindings.CreateMexHttpBinding(), "mex");

            svcHost.Open();
        }
    }
}