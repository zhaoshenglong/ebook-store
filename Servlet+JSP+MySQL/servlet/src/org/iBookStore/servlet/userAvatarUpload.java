package org.iBookStore.servlet;

import com.google.gson.Gson;
import org.iBookStore.HibernateUtil;
import org.iBookStore.entity.ReturnJson;
import org.iBookStore.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.iBookStore.servlet.utility.ServletUtility.setCORS;
@WebServlet("/avatarUpload")
@MultipartConfig(maxFileSize = 4 * 1024 * 1024)
public class userAvatarUpload extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORS(response);
        response.setContentType("application/json");
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        HttpSession httpSession = request.getSession(false);
        PrintWriter out = response.getWriter();

        try{
            /* How to distinguish user image and book image? */
            String name = (String)httpSession.getAttribute("name");
            Part part = request.getPart("avatar");
            System.out.println(part.getSize());
            System.out.println(part.getHeader("Content-Disposition"));
            System.out.println(part.getSubmittedFileName());
            InputStream userAvatar = part.getInputStream();
            Path avatarPath = Paths.get("/home/zhaoshenglong/bookstore/users/" + name + ".jpg");
            OutputStream writeAvatar = new BufferedOutputStream(Files.newOutputStream(avatarPath));
            byte[] buf = new byte[1024];
            int len;
            while( (len = userAvatar.read(buf)) > 0) {
                writeAvatar.write(buf, 0, len);
                System.out.println(len);
            }
            String url = "http://localhost:8088/img?kind=user&name="+name;
            updateAvatar(name, url);
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
            writeAvatar.flush();
            writeAvatar.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            out.flush();
            out.close();
        }
    }
    private void updateAvatar(String name, String url) {
        User user =  HibernateUtil.getSessionFactory().getCurrentSession().get(User.class, name);
        user.setAvatar(url);
        HibernateUtil.getSessionFactory().getCurrentSession().getSession().update(user);
    }
}
