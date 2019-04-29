package org.iBookStore.filter;

import org.iBookStore.entity.ReturnJson;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static org.iBookStore.servlet.utility.ServletUtility.*;
@WebFilter(urlPatterns = {"/cartServlet", "/avatarUpload","/orderServlet"})
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        setCORS((HttpServletResponse) response);
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        PrintWriter out = response.getWriter();
        if (session == null) {
            if (!((HttpServletRequest) request).getMethod().equals("OPTIONS"))
            ((HttpServletResponse) response).setStatus(403);
            System.out.println("no session");
            out.print("Need login");
            return;
        }
        chain.doFilter(request,response);
    }
    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void destroy() {}
}
