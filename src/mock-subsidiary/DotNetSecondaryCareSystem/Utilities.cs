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
        public static WebResponse MakeWebRequest(string baseUrl, string relativeUrl, string hash)
        {
            string url = baseUrl + relativeUrl;

            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create(url);
            webRequest.Method = "GET";
            webRequest.Headers.Add("api_key", "swagger");
            webRequest.Headers.Add("hash", hash);

            HttpStatusCode statusCode = HttpStatusCode.NotFound;
            string response;

            try
            {
                using (HttpWebResponse webResponse = (HttpWebResponse)webRequest.GetResponse())
                {
                    statusCode = webResponse.StatusCode;

                    using (Stream stream = webResponse.GetResponseStream())
                    {
                        using (StreamReader reader = new StreamReader(stream))
                        {
                            response = reader.ReadToEnd();
                        }
                    }
                }
            }
            catch (Exception e)
            {
                response = e.Message;
            }

            return new WebResponse(response, statusCode);
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
