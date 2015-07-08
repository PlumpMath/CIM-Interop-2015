using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    public class OpenHRFile
    {
        public OpenHRFile(string inputFilename, string inputText, string outputFilename)
        {
            InputFilename = inputFilename;
            InputText = inputText;
            OutputFilename = outputFilename;
        }
        
        public string InputFilename { get; private set; }
        public string OutputFilename { get; private set; }
        public string InputText { get; private set; }
        public string OutputText { get; internal set; }

        public OpenHR.OpenHR001OpenHealthRecord OpenHR { get; internal set; }
    }
}
