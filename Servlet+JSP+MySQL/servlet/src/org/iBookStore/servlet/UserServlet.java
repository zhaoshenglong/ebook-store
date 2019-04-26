package org.iBookStore.servlet;

import java.io.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.iBookStore.HibernateUtil;
import org.iBookStore.entity.Order;
import org.iBookStore.entity.ReturnJson;
import org.iBookStore.entity.User;
import static org.iBookStore.servlet.utility.ServletUtility.*;
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
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ReturnJson rs = new ReturnJson();
        PrintWriter out = response.getWriter();

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        try {
            /* @Action -- verify, get */
            String action = request.getParameter("action");
            if (action.equals("verify")) {
                /* Verify email */
                if (request.getParameter("email") != null) {
                    String email = request.getParameter("email");
                    System.out.println(email);
                    if (existEmail(email)) {
                        response.setStatus(404);
                        rs.setMsg("Email exists");
                        out.print(gson.toJson(rs));
                    } else {
                        rs.setMsg("Email can be registered");
                        out.print(gson.toJson(rs));
                    }
                } else if (request.getParameter("name") != null) {
                    String name = request.getParameter("name");
                    if (existUser(name)) {
                        response.setStatus(404);
                        rs.setMsg("Name exists");
                        out.print(gson.toJson(rs));
                    } else {
                        response.setStatus(200);
                        rs.setMsg("Name can be registered");
                        out.print(gson.toJson(rs));
                    }
                } else if (request.getParameter("password") != null){
                    String password = request.getParameter("password");
                    HttpSession httpSession = request.getSession();
                    String name = (String)httpSession.getAttribute("name");
                    String validPassword = (String)httpSession.getAttribute("password");
                    if (name != null) {
                        if (password.equals(validPassword)) {
                            rs.setMsg("Password verified");
                            out.print(gson.toJson(rs));
                        } else {
                            response.setStatus(403);
                            rs.setMsg("Password invalid");
                            out.print(gson.toJson(rs));
                        }
                        return;
                    }
                }
                else {
                    response.setStatus(403);
                    rs.setMsg("Field-allowed:email, name");
                    out.print(gson.toJson(rs));
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
                        response.setStatus(404);
                        rs.setMsg("Password incorrect");
                        out.print(gson.toJson(rs));
                    }

                    else {

                        /* Get session */
                        HttpSession httpSession = request.getSession();
                        /* Establish session */
                        httpSession.setAttribute("name", user.getName());
                        httpSession.setAttribute("password", user.getPassword());
                        httpSession.setAttribute("avatar", user.getAvatar());
                        httpSession.setAttribute("state", user.getState());
                        httpSession.setAttribute("email", user.getEmail());
                        Set<Order> orders = user.getOrderSet();
                        Iterator<Order> iterator = orders.iterator();
                        while(iterator.hasNext()) {
                            Order o = iterator.next();
                            if (o.getState().equals("unpaid")) {
                                httpSession.setAttribute("cart", o);
                                break;
                            }
                        }
                        if (httpSession.getAttribute("cart") == null) {
                            Order cart = new Order();
                            cart.setUserName(user.getName());
                            cart.setOrderItemList(new HashSet<>());
                            cart.setCreateDate(new Timestamp(System.currentTimeMillis()));
                            cart.setState("unpaid");
                            HibernateUtil.getSessionFactory().getCurrentSession().save(cart);
                            httpSession.setAttribute("cart", cart);
                        }
                        Cookie loggedCookie = new Cookie("logged", "true");
                        /* Keep the cookie for 3 days */
                        loggedCookie.setMaxAge(3*60*60*24);
                        response.addCookie(loggedCookie);
                        out.print(gson.toJson(user));
                    }
                }
            } else {
                response.setStatus(403);
                rs.setMsg("Action-Allowed: verify, get");
                out.print(gson.toJson(rs));
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
    /* Update user information */
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
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null) {
            response.setStatus(403);
            rs.setMsg("Need login");
            out.print(gson.toJson(rs));
            return;
        }
        try {
            String name = (String)httpSession.getAttribute("name");
            User user = HibernateUtil.getSessionFactory().getCurrentSession().get(User.class, name);
            if (user != null) {
                EntityUtility.setUser(user, request);
                HibernateUtil.getSessionFactory().getCurrentSession().update(user);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                rs.setMsg("Update ok");
                out.print(gson.toJson(rs));
            }
        } catch (Exception e) {
            rs.setMsg("Update failed");
            response.setStatus(404);
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
        ReturnJson rs = new ReturnJson();
        try {
            String data = getReaderContent(request);
            User user = gson.fromJson(data, User.class);
            if (user.getName() == null) {
                response.setStatus(404);
                rs.setMsg("Name is null");
                out.print(gson.toJson(rs));
                return;
            } else {
                /* Default user state, avatar */
                user.setState("Activated");
                user.setAvatar("http://localhost:8088/img?kind=user&name=" + "default");
                Set<Order> orderSet = new HashSet<>();
                Order cart = new Order();
                cart.setUserName(user.getName());
                cart.setState("unpaid");
                orderSet.add(cart);
                user.setOrderSet(orderSet);
                HibernateUtil.getSessionFactory().getCurrentSession().save(user);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                rs.setMsg("Put success");
                out.print(gson.toJson(rs));
            }
        } catch (Exception e) {
            response.setStatus(500);
            rs.setMsg("Put failed");
            out.print(gson.toJson(rs));
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
