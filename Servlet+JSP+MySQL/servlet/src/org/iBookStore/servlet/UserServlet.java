package org.iBookStore.servlet;

import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.iBookStore.HibernateUtil;
import org.iBookStore.entity.ReturnJson;
import org.iBookStore.entity.User;

import javax.servlet.annotation.WebServlet;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setContentType("application/json");

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        try {
            ReturnJson rs = new ReturnJson();
            String queryString = request.getQueryString();
            String action = queryString.split("=")[0];
            if (action.equals("all")) {
                List<User> users = findAll();
                out.print( gson.toJson(users));
            } else if (action.equals("name")) {
                String name = queryString.split("=")[1];
                if (existUser(name)) {
                    rs.setStatus("404");
                    rs.setMsg("User exists");
                } else {
                    rs.setStatus("200");
                    rs.setMsg("User not exists");
                }
                out.print(gson.toJson(rs));
            } else if (action.equals("email")) {
                String email = queryString.split("=")[1];
                if (existEmail(email)) {
                    rs.setStatus("404");
                    rs.setMsg("Email exists");
                } else {
                    rs.setStatus("200");
                    rs.setMsg("Email not exists");
                }
                out.print(gson.toJson(rs));
            } else {
                String[] args = queryString.split("&");
                if (args.length >= 2) {
                    String passwd = "", name = "";
                    if (args[0].split("=").length >= 2){
                        passwd = queryString.split("&")[0].split("=")[1];
                    }
                    if (args[1].split("=").length >= 2){
                        name = queryString.split("&")[1].split("=")[1];
                    }
                    User user = findUser(name);
                    if (user == null) {
                        rs.setStatus("404");
                        rs.setMsg("User not exists");
                        out.print(gson.toJson(rs));
                    }
                    else if (user.getPassword() != passwd) {
                        rs.setStatus("404");
                        rs.setMsg("Password invalid");
                        out.print(gson.toJson(rs));
                    } else {
                        out.print(new Gson().toJson(user));
                    }
                }
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
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setContentType("application/json");
        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        StringBuffer jb = new StringBuffer();
        String line;
        try {
            request.setCharacterEncoding("utf-8");
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name, email, password;
        System.out.println(jb.toString());
        try {

            JsonElement jsonElement = new JsonParser().parse(jb.toString());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            name = jsonObject.get("name").getAsString();
            password = jsonObject.get("password").getAsString();
            email = jsonObject.get("email").getAsString();
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setBriefAddr("");
            user.setAvatar("default");
            user.setDetailAddr("");
            user.setState("Activated");
            this.addUser(user);
            ReturnJson rs = new ReturnJson();
            rs.setMsg("Register ok");
            rs.setStatus("200");
            out.print(gson.toJson(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setContentType("application/json");
        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        PrintWriter out = response.getWriter();
        try {
            Gson gson = new Gson();
            StringBuffer jb = new StringBuffer();
            String line;
            try {
                request.setCharacterEncoding("utf-8");
                BufferedReader reader = request.getReader();
                while((line = reader.readLine()) != null) {
                    jb.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String name, email, password, action, state;
            System.out.println(jb.toString());
            try {
                JsonElement jsonElement = new JsonParser().parse(jb.toString());
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                name = jsonObject.get("name").getAsString();
                password = jsonObject.get("password").getAsString();
                email = jsonObject.get("email").getAsString();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            out.close();
        }
    }


    /* Utility functions */
    private void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        session.save(user);
        session.getTransaction().commit();
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
    private List<User> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from User ");
        return query.list();
    }
    private void updateUser(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        session.update(user);
        transaction.commit();
    }

}
