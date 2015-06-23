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
        private Dictionary<Guid, Name> _nameMap = new Dictionary<Guid, Name>();
        private NameList _nameList;
        
        public Obfuscator()
        {
        }

        public void Obfuscate(OpenHRFile[] openHRFiles)
        {
            Initialise();

            foreach (OpenHRFile openHRFile in openHRFiles)
            {
                openHRFile.OpenHR = Deserialize(openHRFile.InputText);

                UpdatePersonNames(openHRFile.OpenHR);
                UpdateAllGuidFields(openHRFile.OpenHR);

                openHRFile.OutputText = Serialize(openHRFile.OpenHR);
            }
        }

        private void Initialise()
        {
            _nameList = new NameList();
        }
        
        private void UpdatePersonNames(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            foreach (OpenHR.OpenHR001Person person in openHR.adminDomain.person)
            {
                Name name = GetName(person);

                person.title = name.Title;
                person.forenames = name.Forename;
                person.surname = name.Surname;

                if (!string.IsNullOrEmpty(person.previousSurname))
                    person.previousSurname = name.PreviousSurname;

                if (!string.IsNullOrEmpty(person.callingName))
                    person.callingName = name.Forename;

                if (!string.IsNullOrEmpty(person.birthSurname))
                    person.birthSurname = name.BirthSurname;
            }
        }

        private Name GetName(OpenHR.OpenHR001Person person)
        {
            if (!_nameMap.ContainsKey(person.id))
            {
                Name name;
                
                if (person.sex == OpenHR.vocSex.F)
                    name = _nameList.GetFemaleName(person.title);
                else
                    name = _nameList.GetMaleName(person.title);

                _nameMap.Add(person.id, name);
            }

            return _nameMap[person.id];
        }

        private static OpenHR.OpenHR001OpenHealthRecord Deserialize(string xml)
        {
            return Utilities.Deserialize<OpenHR.OpenHR001OpenHealthRecord>(xml);
        }

        private static string Serialize(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            return Utilities.Serialize<OpenHR.OpenHR001OpenHealthRecord>(openHR);
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
