package org.iBookStore.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.query.Query;
import org.iBookStore.HibernateUtil;
import org.iBookStore.entity.Book;
import org.iBookStore.entity.Order;
import org.iBookStore.entity.ReturnJson;
import org.iBookStore.entity.OrderItem;
import org.iBookStore.servlet.utility.*;
import static org.iBookStore.servlet.utility.CORS.*;

@WebServlet ("/orderServlet")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        setCORS(response);
        response.setContentType("application/json");
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List orders = null;
        try{
            String role = request.getParameter("role");
            String action = request.getParameter("action");
            if (role.equals("admin")) {
                if (action.equals("findAll")) {
                    orders = findAll();
                    out.print(gson.toJson(orders));
                } else if (action.equals("findByUser")) {
                    String userName = request.getParameter("user");
                    orders = findByUser(userName, 0);
                    out.print(gson.toJson(orders));
                } else if (action.equals("findBetween")) {
                    Timestamp begin = Timestamp.valueOf(request.getParameter("begin"));
                    Timestamp end = Timestamp.valueOf(request.getParameter("end"));
                    orders = findBetweenDate(begin, end);
                    out.print(gson.toJson(orders));
                } else {
                    return;
                }
            } else if (role.equals("user")) {
                if (action.equals("findByUser")) {
                    String userName = request.getParameter("user");
                    orders = findByUser(userName, 1);
                    out.print(gson.toJson(orders));
                } else if(action.equals("findCart")) {
                    String userName = request.getParameter("user");
                    Order c = findCart(userName);
                    out.print(gson.toJson(c));
                }
            } else {
                ReturnJson errorJson = new ReturnJson();
                errorJson.setStatus("404");
                errorJson.setMsg("Role not found");
                return;
            }
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            out.print("error");
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        setCORS(response);
        response.setContentType("application/json");
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        ReturnJson errorJson = new ReturnJson();
        errorJson.setStatus("404");
        ReturnJson successJson = new ReturnJson();
        successJson.setStatus("200");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try{
            String userName = request.getParameter("user");
            String action = request.getParameter("action");
            Order cart = findCart(userName);
            String cartId;
            Set<OrderItem> orderItemList = gson.fromJson(request.getParameter("orderItemList"), new TypeToken<Set<OrderItem>>(){}.getType());
            /* Add to cart, create if not exists */
            if (action.equals("add")) {
                /* Cart not exists, create first */
                if (cart == null) {
                    cart = new Order();
                    cart.setCreateDate(new Timestamp(System.currentTimeMillis()));
                    cart.setState("unpaid");
                    cart.setUserName(userName);
                    String timeString = new SimpleDateFormat("yyyyMMddHHmmss").format(cart.getCreateDate());
                    cart.setId(StringUtility.hash(userName) + timeString + (System.currentTimeMillis() & 0xff));
                    cartId = cart.getId();
                    System.out.println(cartId);
                }
                /*
                cartId = cart.getId();
                Set<OrderItem> orderItemSet = ((Order)HibernateUtil.getSessionFactory().getCurrentSession().get(cartId, Order.class)).getOrderItemList();
                HibernateUtil.getSessionFactory().getCurrentSession().delete(orderItemSet);
                HibernateUtil.getSessionFactory().getCurrentSession().saveOrUpdate(orderItemList);
                */
                cart.setId(cart.getId());
                cart.setOrderItemList(orderItemList);
                //HibernateUtil.getSessionFactory().getCurrentSession().saveOrUpdate(cart);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                successJson.setMsg("Add ok");
                out.print(gson.toJson(successJson));
            } else if (action.equals("clear")) {
                /* Assert that cart exists */
                cart.setState("paid");
                cart.setId(cart.getId());
                cart.setCreateDate(new Timestamp(System.currentTimeMillis()));
                for (OrderItem oi : orderItemList) {
                    Book book = (Book)HibernateUtil.getSessionFactory().getCurrentSession().get(oi.getBookId(), Book.class);
                    book.setStock(book.getStock() - oi.getQuantity());
                }
                HibernateUtil.getSessionFactory().getCurrentSession().saveOrUpdate(cart);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                successJson.setMsg("Success buy books");
                out.print(gson.toJson(successJson));
            }
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            errorJson.setMsg("Post failed");
            out.print(gson.toJson(errorJson));
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        setCORS(response);
    }

    private List<Order> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery("from Order ");
        return query.list();
    }
    private List findByUser(String userName, int flag) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        if (flag == 1) {
            /* Return paid order */
            Query query = session.createQuery("from Order where userName=?1 and state = 'paid'");
            query.setParameter(1, userName);
            return query.list();
        } else {
            /* Return all orders, including unpaid*/
            Query query = session.createQuery("from Order where userName=?1");
            query.setParameter(1, userName);
            return query.list();
        }
    }
    private List findBetweenDate(Timestamp begin, Timestamp end) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery("from Order where createDate between ?1 and ?2");
        query.setParameter(1, begin);
        query.setParameter(2, end);
        return query.list();
    }
    private Order findCart(String userName) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery(
                "from Order where userName = ?1 and state='unpaid'");
        query.setParameter(1, userName);
        List<Order> orders = query.list();
        if (orders.size() == 0) {
            return null;
        } else return orders.get(0);
    }
}
