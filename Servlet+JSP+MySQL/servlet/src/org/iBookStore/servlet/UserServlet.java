package org.iBookStore.servlet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.iBookStore.HibernateUtil;
import org.iBookStore.entity.ErrorJson;
import org.iBookStore.entity.User;

import javax.servlet.annotation.WebServlet;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            ErrorJson rs = new ErrorJson();
            String queryString = request.getQueryString();
            String action = queryString.split("&")[0].split("=")
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

    /* Utility functions */
    private void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        session.save(user);
    }
    private boolean existUser(String name) {
        if (findUser(name) == null)
            return false;
        else return true;
    }
    private boolean existEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from User where email = ?1");
        query.setParameter(1, email);
        List<User> users = query.getResultList();
        if (users.size() > 0)
            return true;
        else return false;
    }
    private User findUser(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from User where name = ?1");
        query.setParameter(1, name);
        List<User> users = query.getResultList();
        if (users.size() > 0)
            return users.get(0);
        else return null;
    }

}
