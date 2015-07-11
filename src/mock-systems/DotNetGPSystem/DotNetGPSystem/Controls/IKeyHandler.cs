using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DotNetGPSystem
{
    internal interface IKeyHandler
    {
        bool ProcessKey(Keys key);
    }
}
