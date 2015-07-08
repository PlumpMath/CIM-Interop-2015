using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class GuidObfuscator : IObfuscator
    {
        private Dictionary<Guid, Guid> _guidMap = new Dictionary<Guid, Guid>();

        public void Obfuscate(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            UpdateAllGuidFields(openHR);
        }

        private void UpdateAllGuidFields(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            ReflectionHelper.ReplaceObjectsOfType<System.Guid>(openHR, ReplaceGuid);
        }

        private bool ReplaceGuid(Guid original, out Guid replacement)
        {
            if (original != Guid.Empty)
            {
                if (!_guidMap.ContainsKey(original))
                    _guidMap.Add(original, Guid.NewGuid());

                replacement = _guidMap[original];

                return true;
            }
            else
            {
                replacement = Guid.Empty;
                return false;
            }
        }
    }
}
