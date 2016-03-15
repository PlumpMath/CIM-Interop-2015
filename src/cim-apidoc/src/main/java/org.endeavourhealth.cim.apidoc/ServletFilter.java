package org.endeavourhealth.cim.apidoc;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;

public class ServletFilter implements Filter
{
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        try
        {
            if (request instanceof HttpServletRequest)
            {
                URL url = new URL(((HttpServletRequest)request).getRequestURL().toString());

                if (url != null)
                    if (!url.getFile().contains("."))
                        response.setContentType("text/html");
            }
        }
        catch (Exception e)
        {
            // do nothing
        }

        filterChain.doFilter(request, response);
    }

    public void destroy()
    {
    }
}
