using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class DateObfuscator : IObfuscator
    {
        private const int MillisecondsInADay = (24 * 60 * 60 * 1000);

        private Dictionary<DateTime, DateTime> _dateMap = new Dictionary<DateTime, DateTime>();
        private Random _random = new Random(DateTime.Now.Millisecond);
        private int _daysOffset;
        private long _millisecondsOffset;
        
        public DateObfuscator()
        {
            _daysOffset = _random.Next(1, 90);
            _millisecondsOffset = _random.Next(2000, MillisecondsInADay - 1);
        }

        public void Obfuscate(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            UpdateAllDateFields(openHR);
        }

        private void UpdateAllDateFields(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            ReflectionHelper.ReplaceObjectsOfType<System.DateTime>(openHR, ReplaceDateTime);
        }

        private DateTime ReplaceDateTime(DateTime original)
        {
            if (original == default(DateTime))
                return original;
            
            if (!_dateMap.ContainsKey(original))
                _dateMap.Add(original, GetNewDate(original));

            return _dateMap[original];
        }

        private DateTime GetNewDate(DateTime original)
        {
            if ((original.Day == 1) && (original.Date == original))  // possibly a partial date - would be better to pass date part through also
                return original;

            DateTime replacement = original.AddDays(_daysOffset);

            if (original.Date != original)
                replacement.AddMilliseconds(_millisecondsOffset);

            return replacement;
        }
    }
}
