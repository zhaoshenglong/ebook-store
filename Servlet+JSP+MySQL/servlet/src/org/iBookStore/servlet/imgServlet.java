package org.iBookStore.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import org.iBookStore.entity.ReturnJson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.iBookStore.servlet.utility.ServletUtility.*;
@WebServlet(urlPatterns = "/img")
public class imgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("image/jpg");
        OutputStream out = response.getOutputStream();
        String queryString = request.getQueryString();
        String imgName = queryString.split("&")[1].split("=")[1];
        String imgKind = queryString.split("&")[0].split("=")[1];
        imgName = new String(imgName.getBytes(), "utf-8");
        try {
            Path imgPath = null;
            if (imgKind.equals("book")){
                imgPath = Paths.get("/home/zhaoshenglong/bookstore/books/" + imgName + ".jpg");
            }
            else if (imgKind.equals("user"))
                imgPath =  Paths.get("/home/zhaoshenglong/bookstore/users/" + imgName + ".jpg");
            InputStream in = new BufferedInputStream(Files.newInputStream(imgPath));
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) >= 0) {
                out.write(buf, 0, len);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORS(response);
        HttpSession httpSession = request.getSession(false);
        ReturnJson rs = new ReturnJson();
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        if (httpSession == null) {
            rs.setMsg("Need log in");
            response.setStatus(403);
            out.print(gson.toJson(rs));
            return;
        }
        doGet(request, response);
    }
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORS(response);
    }
}
