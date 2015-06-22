using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    public class Obfuscator
    {
        private Dictionary<Guid, Guid> _guidMap = new Dictionary<Guid, Guid>();
        
        public Obfuscator()
        {
        }

        public void Obfuscate(OpenHRFile[] openHRFiles)
        {
            foreach (OpenHRFile openHRFile in openHRFiles)
            {
                openHRFile.OpenHR = Utilities.Deserialize<OpenHR.OpenHR001OpenHealthRecord>(openHRFile.InputText);

                UpdateAllGuidFields(openHRFile.OpenHR);

                openHRFile.OutputText = Utilities.Serialize<OpenHR.OpenHR001OpenHealthRecord>(openHRFile.OpenHR);
            }
        }

        private void UpdateAllGuidFields(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            Stack<object> objects = new Stack<object>();

            objects.Push(openHR);

            while (objects.Count > 0)
            {
                object nextObject = objects.Pop();

                foreach (FieldInfo field in nextObject.GetType().GetFields())
                {
                    if (field.FieldType == typeof(System.Guid))
                    {
                        Guid currentGuid = (Guid)field.GetValue(nextObject);

                        if (currentGuid != Guid.Empty)
                        {
                            if (!_guidMap.ContainsKey(currentGuid))
                                _guidMap.Add(currentGuid, Guid.NewGuid());

                            Guid newGuid = _guidMap[currentGuid];

                            if (currentGuid != Guid.Empty)
                                field.SetValue(nextObject, newGuid);
                        }
                    }
                    else if (field.FieldType.IsArray)
                    {
                        object array = field.GetValue(nextObject);

                        if (array != null)
                            foreach (object item in (array as IEnumerable))
                                objects.Push(item);
                        
                    }
                    else if (field.FieldType.IsClass)
                    {
                        object nextNextObject = field.GetValue(nextObject);

                        if (nextNextObject != null)
                            if (field.FieldType != nextObject.GetType())
                                objects.Push(nextNextObject);
                    }
                }
            }
        }
    }
}
