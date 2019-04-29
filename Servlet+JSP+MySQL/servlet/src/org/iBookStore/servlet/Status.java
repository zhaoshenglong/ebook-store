package org.iBookStore.servlet;

import org.iBookStore.entity.ReturnJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import static org.iBookStore.servlet.utility.ServletUtility.*;
@WebServlet("/status")
public class Status extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        setCORS(response);
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        if (session == null) {
            response.setStatus(403);
        } else {
            response.setStatus(200);
            String name = (String)session.getAttribute("name");
            String avatar = (String)session.getAttribute("avatar");
            String email = (String)session.getAttribute("email");
            out.print("{\"name\":\"" + name+ "\", \"avatar\":\"" + avatar + "\",\"email\":\""+ email + "\"}");
            out.flush();
            out.close();
        }
    }
}
