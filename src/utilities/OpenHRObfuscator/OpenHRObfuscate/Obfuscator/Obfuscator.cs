using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class Obfuscator
    {
        public Obfuscator()
        {
        }

        private IObfuscator[] GetObfuscators()
        {
            return new List<IObfuscator>()
            {            
                new PersonNameObfuscator(),
                new OrganisationAndLocationObfuscator(),
                new AddressObfuscator(),
                new GuidObfuscator(),
                new ArrayObfuscator(),
                new DateObfuscator()
            }
            .ToArray();
        }

        public void Obfuscate(OpenHRFile[] openHRFiles)
        {
            IObfuscator[] obfuscators = GetObfuscators();

            foreach (OpenHRFile openHRFile in openHRFiles)
            {
                openHRFile.OpenHR = Utilities.Deserialize<OpenHR.OpenHR001OpenHealthRecord>(openHRFile.InputText);

                foreach (IObfuscator obfuscator in obfuscators)
                    obfuscator.Obfuscate(openHRFile.OpenHR);

                openHRFile.OutputText = Utilities.Serialize<OpenHR.OpenHR001OpenHealthRecord>(openHRFile.OpenHR);
            }
        }
    }
}
