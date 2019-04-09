package org.iBookStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(urlPatterns = "/img")
public class imgRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        String imgName = request.getQueryString();
        imgName = imgName.split("=")[1];
        imgName = new String(imgName.getBytes(), "utf-8");
        try {
            Path imgPath = Paths.get("/home/zhaoshenglong/bookstore/books/" + imgName + ".jpg");
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
        doGet(request, response);
    }
}
