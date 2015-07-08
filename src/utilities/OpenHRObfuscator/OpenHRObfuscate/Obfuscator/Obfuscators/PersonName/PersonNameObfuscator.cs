using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class PersonNameObfuscator : IObfuscator
    {
        private NameList _nameList = new NameList();
        private Dictionary<Guid, Name> _nameMap = new Dictionary<Guid, Name>();

        public void Obfuscate(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            UpdatePersonNames(openHR);
        }

        private void UpdatePersonNames(OpenHR.OpenHR001OpenHealthRecord openHR)
        {
            foreach (OpenHR.OpenHR001Person person in openHR.adminDomain.person)
            {
                Name name = GetNameReplacement(person);

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

        private Name GetNameReplacement(OpenHR.OpenHR001Person person)
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
    }
}
