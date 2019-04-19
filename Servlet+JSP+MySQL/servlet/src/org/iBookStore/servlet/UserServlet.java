package org.iBookStore.servlet;

import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.iBookStore.HibernateUtil;
import org.iBookStore.entity.ReturnJson;
import org.iBookStore.entity.User;
import static org.iBookStore.servlet.utility.CORS.*;
import static org.iBookStore.servlet.utility.StringUtility.*;
import org.iBookStore.servlet.utility.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORS(response);
        response.setContentType("application/json");

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();

        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ReturnJson errorJson = new ReturnJson();
        errorJson.setStatus("404");
        ReturnJson successJson = new ReturnJson();
        successJson.setStatus("200");
        try {
            /* @Action -- verify, get */
            String action = request.getParameter("action");
            if (action.equals("verify")) {
                /* Verify email */
                if (request.getParameter("email") != null) {
                    String email = request.getParameter("email");
                    System.out.println(email);
                    if (existEmail(email)) {
                        errorJson.setMsg("Email exists");
                        out.print(gson.toJson(errorJson));
                    } else {
                        successJson.setMsg("Email can be registered");
                        out.print(gson.toJson(successJson));
                    }
                } else if (request.getParameter("name") != null) {
                    String name = request.getParameter("name");
                    if (existUser(name)) {
                        errorJson.setMsg("Name exists");
                        out.print(gson.toJson(errorJson));
                    } else {
                        successJson.setMsg("Name can be registered");
                        out.print(gson.toJson(successJson));
                    }
                } else {
                    errorJson.setMsg("Field-allowed:email, name");
                    out.print(gson.toJson(errorJson));
                }
            } else if(action.equals("get")){
                if (request.getParameter("all") != null) {
                    List<User> users;
                    users = findAll();
                    out.print(gson.toJson(users));
                } else if (request.getParameter("name") != null) {
                    List<User> users = findUser(request.getParameter("name"));
                    User user = users.get(0);
                    if(!user.getPassword().equals(request.getParameter("password"))){
                        errorJson.setMsg("Password incorrect");
                        out.print(gson.toJson(errorJson));
                    }

                    else out.print(gson.toJson(user));
                }
            } else {
                errorJson.setMsg("Action-Allowed: verify, get");
                out.print(gson.toJson(errorJson));
            }
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORS(response);
        response.setContentType("application/json");
        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        ReturnJson rs = new ReturnJson();
        try {
            String name = request.getParameter("name");
            User user = HibernateUtil.getSessionFactory().getCurrentSession().get(User.class, name);
            if (user != null) {
                EntityUtility.setUser(user, request);
                HibernateUtil.getSessionFactory().getCurrentSession().update(user);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                rs.setMsg("Update ok");
                rs.setStatus("200");
                out.print(gson.toJson(rs));
            }
        } catch (Exception e) {
            rs.setMsg("Update failed");
            rs.setStatus("404");
            out.print(gson.toJson(rs));
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORS(response);
        response.setContentType("application/json");
        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        ReturnJson errorJson = new ReturnJson();
        errorJson.setStatus("404");
        ReturnJson successJson = new ReturnJson();
        successJson.setStatus("200");
        try {
            String data = getReaderContent(request);
            User user = gson.fromJson(data, User.class);
            if (user.getName() == null) {
                errorJson.setMsg("Name is null");
                out.print(gson.toJson(errorJson));
                return;
            } else {
                user.setState("Activated");
                user.setAvatar("http://localhost:8088/img?kind=user&name=" + "default");
                HibernateUtil.getSessionFactory().getCurrentSession().save(user);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                successJson.setMsg("Put success");
                out.print(gson.toJson(successJson));
            }
        } catch (Exception e) {
            errorJson.setMsg("Put failed");
            out.print(gson.toJson(errorJson));
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            out.close();
        }
    }
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        setCORS(response);
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
        List<User> users = query.list();
        if (users.size() > 0)
            return true;
        else return false;
    }
    private List<User> findUser(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from User where name = ?1");
        query.setParameter(1, name);
        List<User> users = query.list();
        if (users.size() > 0)
            return users;
        else return null;
    }
    private List<User> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from User ");
        return query.list();
    }
}
