using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class Name
    {
        public Name(string title, string forename, string surname, string previousSurname, string birthSurname)
        {
            Title = title;
            Forename = forename;
            Surname = surname;
            PreviousSurname = previousSurname;
            BirthSurname = birthSurname;
        }
        
        public string Title { get; private set; }
        public string Forename { get; private set; }
        public string Surname { get; private set; }
        public string PreviousSurname { get; private set; }
        public string BirthSurname { get; private set; }
    }
}
