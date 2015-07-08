using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal class NameList
    {
        private const string ResourcePrefix = "OpenHRObfuscate.Obfuscator.Data.Name";
        private const string MaleFirstNameNameListResourceName = ResourcePrefix + ".MaleFirstNames.txt";
        private const string FemaleFirstNameListResourceName = ResourcePrefix + ".FemaleFirstNames.txt";
        private const string LastNameListResourceName = ResourcePrefix + ".LastNames.txt";

        private string[] _maleFirstNames;
        private string[] _femaleFirstNames;
        private string[] _lastNames;

        private Random _random = new Random();


        public NameList()
        {
            LoadNames();
        }

        private void LoadNames()
        {
            _maleFirstNames = LoadNames(MaleFirstNameNameListResourceName);
            _femaleFirstNames = LoadNames(FemaleFirstNameListResourceName);
            _lastNames = LoadNames(LastNameListResourceName);
        }

        private string[] LoadNames(string resourceName)
        {
            return Utilities.LoadStringResource(MaleFirstNameNameListResourceName)
                .Split(new string[] { Environment.NewLine }, StringSplitOptions.RemoveEmptyEntries)
                .Select(t => t.ToTitleCase())
                .ToArray();            
        }

        public Name GetMaleName(string title)
        {
            return new Name(
                title: title,
                forename: GetRandomItem(_maleFirstNames),
                surname: GetRandomItem(_lastNames),
                previousSurname: GetRandomItem(_lastNames),
                birthSurname: GetRandomItem(_lastNames));
        }

        public Name GetFemaleName(string title)
        {
            return new Name(
                title: title,
                forename: GetRandomItem(_femaleFirstNames),
                surname: GetRandomItem(_lastNames),
                previousSurname: GetRandomItem(_lastNames),
                birthSurname: GetRandomItem(_lastNames));
        }

        private string GetRandomItem(string[] items)
        {
            if (items.Length == 0)
                return null;
            
            int randomNumber = _random.Next(0, (items.Length - 1));

            return items[randomNumber];
        }
    }
}
