using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace DotNetSecondaryCareSystem
{
    internal static class Utilities
    {
        public static WebResponse GetCimUrl(string baseUrl, string relativeUrl, string apiKey, string hash)
        {
            return MakeWebRequest("GET", baseUrl, relativeUrl, apiKey, hash, string.Empty);
        }

        public static WebResponse PostCimUrl(string baseUrl, string relativeUrl, string apiKey, string hash, string payload)
        {
            return MakeWebRequest("POST", baseUrl, relativeUrl, apiKey, hash, payload);
        }

        public static WebResponse PutCimUrl(string baseUrl, string relativeUrl, string apiKey, string hash, string payload)
        {
            return MakeWebRequest("PUT", baseUrl, relativeUrl, apiKey, hash, payload);
        }

        public static WebResponse MakeWebRequest(string method, string baseUrl, string relativeUrl, string apiKey, string hash, string payload)
        {
            string url = baseUrl + relativeUrl;

            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create(url);
            webRequest.Method = method;
            webRequest.Headers.Add("api_key", apiKey);
            webRequest.Headers.Add("hash", hash);
            
            HttpStatusCode statusCode = HttpStatusCode.NotFound;
            string response = string.Empty;

            try
            {
                if (!string.IsNullOrEmpty(payload))
                {
                    byte[] data = Encoding.ASCII.GetBytes(payload);

                    webRequest.ContentType = "application/json";
                    webRequest.ContentLength = data.Length;

                    using (Stream stream = webRequest.GetRequestStream())
                        stream.Write(data, 0, data.Length);
                }

                using (HttpWebResponse webResponse = (HttpWebResponse)webRequest.GetResponse())
                {
                    statusCode = webResponse.StatusCode;
                    response = GetWebResponseBody(webResponse);
                }
            }
            catch (WebException e)
            {
                HttpWebResponse webResponse = (HttpWebResponse)e.Response;

                statusCode = webResponse.StatusCode;
                response = GetWebResponseBody(webResponse);
            }
            catch (Exception e2)
            {
                response = e2.Message;
            }

            return new WebResponse(response, statusCode);
        }

        private static string GetWebResponseBody(HttpWebResponse webResponse)
        {
            using (Stream stream = webResponse.GetResponseStream())
                using (StreamReader reader = new StreamReader(stream))
                    return reader.ReadToEnd();
        }

        public static string GenerateHash(string restPath, string body)
        {
            string privateKey = "privateKey";

            string data = FormatRestPathForHashGeneration(restPath);
            
            if (data != null)
                data += body;

            HMAC mac = HMAC.Create("HmacSHA256");
            ASCIIEncoding encoder = new ASCIIEncoding();
            mac.Key = encoder.GetBytes(privateKey);
            mac.Initialize();
            byte[] digest = mac.ComputeHash(encoder.GetBytes(data));

            return Convert.ToBase64String(digest);
        }

        private static string FormatRestPathForHashGeneration(string restPath)
        {
            string result = restPath;
            
            int questionMarkPosition = restPath.IndexOf('?');

            if (questionMarkPosition >= 0)
                result = restPath.Substring(0, questionMarkPosition) + "/";

            return result;
        }

        public static string FormatRestPath(string restMethodString, params object[] args)
        {
            string formattableString = FromRestPathToFormattableString(restMethodString);

            return string.Format(formattableString, args);
        }

        private static string FromRestPathToFormattableString(string restMethodString)
        {
            string result = string.Empty;
            bool insideBraces = false;
            int formatCount = 0;
            
            foreach (char c in restMethodString)
            {
                if (c == '{')
                {
                    insideBraces = true;
                    result += "{" + formatCount++.ToString();
                }
                else if (c == '}')
                {
                    result += "}";
                    insideBraces = false;
                }
                else
                { 
                    if (!insideBraces)
                        result += c;
                }
            }

            return result;
        }

        public static string FormatJson(string json)
        {
            using (StringReader stringReader = new StringReader(json))
            {
                using (StringWriter stringWriter = new StringWriter())
                {
                    using (JsonTextReader jsonReader = new JsonTextReader(stringReader))
                    {
                        using (JsonTextWriter jsonWriter = new JsonTextWriter(stringWriter) { Formatting = Formatting.Indented })
                        {
                            jsonWriter.WriteToken(jsonReader);
                            return stringWriter.ToString();
                        }
                    }
                }
            }
        }
    }
}
