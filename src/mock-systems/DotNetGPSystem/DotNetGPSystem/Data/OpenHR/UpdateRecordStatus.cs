using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DotNetGPSystem
{
    [Serializable]
    public enum UpdateRecordStatus : int
    {
        Successful = 0,
        PatientNotFound = -1,
        IdentifierAlreadyInUse = -2
    }
}
