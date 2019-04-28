package org.iBookStore.filter;

import org.iBookStore.entity.ReturnJson;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
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
        ReturnJson rs = new ReturnJson();
        if (session == null) {
            rs.setMsg("Need login in");
            out.print(rs);
            return;
        }
        chain.doFilter(request,response);
    }
    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void destroy() {}
}
