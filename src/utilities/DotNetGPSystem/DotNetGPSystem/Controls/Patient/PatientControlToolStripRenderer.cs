using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DotNetGPSystem
{
    internal class PatientControlToolStripRenderer : ToolStripProfessionalRenderer
    {
        protected override void OnRenderButtonBackground(ToolStripItemRenderEventArgs e)
        {
            var btn = e.Item as ToolStripButton;

            if (btn != null && btn.CheckOnClick && btn.Checked)
            {
                Rectangle bounds = new Rectangle(Point.Empty, e.Item.Size);
                e.Graphics.FillRectangle(new SolidBrush(Color.FromArgb(51, 153, 255)), bounds);

                Rectangle bounds2 = new Rectangle(new Point(1, 1), new Size(e.Item.Width - 2, e.Item.Height - 2));
                e.Graphics.FillRectangle(new SolidBrush(Color.FromArgb(153, 204, 255)), bounds2);
            }
            else
            {
                base.OnRenderButtonBackground(e);
            }
        }
    }
}
