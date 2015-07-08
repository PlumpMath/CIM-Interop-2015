using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class ArrayObfuscator : IObfuscator
    {
        public void Obfuscate(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            ReorderArrays(openHR);
        }

        private void ReorderArrays(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            ReflectionHelper.ReorderArrays(openHR);
        }
    }
}
