using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.ServiceModel.Channels;
using System.ServiceModel.Description;
using System.ServiceModel.Dispatcher;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    internal delegate void LogMessage(ApiLogMessage message);
    
    internal class GPApiServiceLogger : IDispatchMessageInspector, IServiceBehavior
    {
        private LogMessage _logMessage;
        private string _requestMessage;
        
        public GPApiServiceLogger(LogMessage logMessage)
        {
            _logMessage = logMessage;
        }
        
        public object AfterReceiveRequest(ref System.ServiceModel.Channels.Message request, System.ServiceModel.IClientChannel channel, System.ServiceModel.InstanceContext instanceContext)
        {
            _requestMessage = GetRequestMessageTextWithHeaders(request);

            return null;
        }

        public void BeforeSendReply(ref System.ServiceModel.Channels.Message reply, object correlationState)
        {
            _logMessage(new ApiLogMessage(DateTime.Now, _requestMessage, GetResponseMessageTextWithHeaders(reply)));

            _requestMessage = string.Empty;
        }

        public void AddBindingParameters(ServiceDescription serviceDescription, System.ServiceModel.ServiceHostBase serviceHostBase, System.Collections.ObjectModel.Collection<ServiceEndpoint> endpoints, System.ServiceModel.Channels.BindingParameterCollection bindingParameters)
        {
            
        }

        public void ApplyDispatchBehavior(ServiceDescription serviceDescription, System.ServiceModel.ServiceHostBase serviceHostBase)
        {
            foreach (ChannelDispatcher dispatcher in serviceHostBase.ChannelDispatchers)
                foreach (var endpoint in dispatcher.Endpoints)
                    endpoint.DispatchRuntime.MessageInspectors.Add(new GPApiServiceLogger(_logMessage));
        }

        public void Validate(ServiceDescription serviceDescription, System.ServiceModel.ServiceHostBase serviceHostBase)
        {
            
        }

        private string GetRequestMessageTextWithHeaders(System.ServiceModel.Channels.Message request)
        {
            string message = string.Empty;
            
            HttpRequestMessageProperty httpRequest = (HttpRequestMessageProperty)request.Properties[HttpRequestMessageProperty.Name];

            message = httpRequest.Method + " " + request.Headers.To.PathAndQuery + " HTTP/1.1" + Environment.NewLine;

            foreach (var header in httpRequest.Headers)
                message += header.ToString() + ": " + httpRequest.Headers.Get(header.ToString()) + Environment.NewLine;

            message += Environment.NewLine;
            message += request.ToString();

            return message;
        }

        private string GetResponseMessageTextWithHeaders(System.ServiceModel.Channels.Message response)
        {
            string message = string.Empty;

            if (response.Properties.ContainsKey(HttpResponseMessageProperty.Name))
            {
                HttpResponseMessageProperty httpResponse = (HttpResponseMessageProperty)response.Properties[HttpResponseMessageProperty.Name];

                message = "HTTP/1.1 " + ((int)httpResponse.StatusCode).ToString() + " " + httpResponse.StatusCode.ToString() + " " + Environment.NewLine;

                foreach (var header in httpResponse.Headers)
                    message += header.ToString() + ": " + httpResponse.Headers.Get(header.ToString()) + Environment.NewLine;

                message += Environment.NewLine;
            }

            message += response.ToString();

            return message;
        }
    }
}
