using System;
using System.Text;
using System.Drawing;
using System.Windows.Forms;
using System.Runtime.InteropServices;


namespace DotNetGPSystem
{
    internal class MessageBox : IDisposable
    {
        public static void Show(Form owner, string message, MessageBoxIcon icon)
        {
            using (new MessageBox(owner))
                System.Windows.Forms.MessageBox.Show(message, owner.Text, MessageBoxButtons.OK, icon);
        }
        
        private int _tries = 0;
        private Form _owner;

        private MessageBox(Form owner)
        {
            _owner = owner;
            owner.BeginInvoke(new MethodInvoker(FindDialog));
        }

        private void FindDialog()
        {
            if (_tries < 0) 
                return;

            EnumThreadWndProc callback = new EnumThreadWndProc(CheckWindow);
            
            if (EnumThreadWindows(GetCurrentThreadId(), callback, IntPtr.Zero))
                if (++_tries < 10) 
                    _owner.BeginInvoke(new MethodInvoker(FindDialog));
        }
        
        private bool CheckWindow(IntPtr hWnd, IntPtr lp)
        {
            StringBuilder sb = new StringBuilder(260);
            GetClassName(hWnd, sb, sb.Capacity);

            if (sb.ToString() != "#32770") 
                return true;
            
            Rectangle frmRect = new Rectangle(_owner.Location, _owner.Size);

            RECT dlgRect;
            
            GetWindowRect(hWnd, out dlgRect);
            
            MoveWindow(hWnd,
                frmRect.Left + (frmRect.Width - dlgRect.Right + dlgRect.Left) / 2,
                frmRect.Top + (frmRect.Height - dlgRect.Bottom + dlgRect.Top) / 2,
                dlgRect.Right - dlgRect.Left,
                dlgRect.Bottom - dlgRect.Top, true);
            
            return false;
        }

        public void Dispose()
        {
            _tries = -1;
        }

        private delegate bool EnumThreadWndProc(IntPtr hWnd, IntPtr lp);

        [DllImport("user32.dll")]
        private static extern bool EnumThreadWindows(int tid, EnumThreadWndProc callback, IntPtr lp);
        
        [DllImport("kernel32.dll")]
        private static extern int GetCurrentThreadId();
        
        [DllImport("user32.dll")]
        private static extern int GetClassName(IntPtr hWnd, StringBuilder buffer, int buflen);
        
        [DllImport("user32.dll")]
        private static extern bool GetWindowRect(IntPtr hWnd, out RECT rc);
        
        [DllImport("user32.dll")]
        private static extern bool MoveWindow(IntPtr hWnd, int x, int y, int w, int h, bool repaint);
        
        private struct RECT 
        { 
            public int Left; 
            public int Top; 
            public int Right; 
            public int Bottom; 
        }
    }
}
