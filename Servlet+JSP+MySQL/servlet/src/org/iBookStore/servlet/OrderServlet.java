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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.query.Query;
import org.iBookStore.HibernateUtil;
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
                    //orders = findAll();
                    List<Order> dd= findAll();
                    out.print(gson.toJson(dd));
                } else if (action.equals("findByUser")) {
                    String userName = request.getParameter("user");
                    orders = findByUser(userName, 0);
                } else if (action.equals("findBetween")) {
                    Timestamp begin = Timestamp.valueOf(request.getParameter("begin"));
                    Timestamp end = Timestamp.valueOf(request.getParameter("end"));
                    orders = findBetweenDate(begin, end);
                } else {
                    return;
                }
            } else if (role.equals("user")) {
                if (action.equals("findByUser")) {
                    String userName = request.getParameter("user");
                    orders = findByUser(userName, 1);
                } else if(action.equals("findCart")) {
                    String userName = request.getParameter("user");
                    orders = findCartByUser(userName);
                }
            } else {
                ReturnJson errorJson = new ReturnJson();
                errorJson.setStatus("404");
                errorJson.setMsg("Role not found");
                return;
            }
            out.print(gson.toJson(orders));
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
            Order cartOrder = findCart(userName);
            String cartId;
            /* Add to cart, create if not exists */
            if (action.equals("add")) {
                /* Cart not exists, create first */
                if (cartOrder == null) {
                    Order cart = new Order();
                    cart.setCreateDate(new Timestamp(System.currentTimeMillis()));
                    cart.setState("unpaid");
                    cart.setUserName(userName);
                    String timeString = new SimpleDateFormat("yyyyMMddHHmmss").format(cart.getCreateDate());
                    cart.setId(StringUtility.hash(userName) + timeString + (System.currentTimeMillis() & 0xff));
                    HibernateUtil.getSessionFactory().getCurrentSession().save(cart);
                    cartId = cart.getId();
                    System.out.println(cartId);
                }
                else cartId = cartOrder.getId();
                List<OrderItem> cartItem;
                /* Get OrderItem data */
                List<OrderItem> itemList;
                String data = StringUtility.getReaderContent(request);
                System.out.println(data);

                /* Deserialize from json data */
                itemList = gson.fromJson(data, new TypeToken<List<OrderItem>>(){}.getType());
                for (OrderItem oi : itemList ) {
                    //oi.setOrderId(cartId);
                    HibernateUtil.getSessionFactory().getCurrentSession().save(oi);
                }
            } else if (action.equals("clear")) {
                List<OrderItem> itemList = new ArrayList<OrderItem>();
                String data = StringUtility.getReaderContent(request);
            }
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
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
        /*Query query = session.createQuery(
                "select o.userName, o.createDate, o.state, o.id, " +
                "b.isbn,b.name,b.price,oi.quantity " +
                "from  Order as o, OrderItem as oi, Book as b " +
                //"where o.id=oi.orderId " +
                        "where oi.bookId=b.id ");*/
        Query query = session.createQuery("from Order ");
        return query.list();
    }
    private List findByUser(String userName, int flag) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        if (flag == 1) {
            /* Return paid order */
            Query query = session.createQuery(
                    "select o.userName, o.createDate, o.state, o.id, " +
                            "b.isbn,b.name,b.price,oi.quantity " +
                            "from  Order as o, OrderItem as oi, Book as b " +
                            "where o.id=oi.orderId and oi.bookId=b.id and o.userName=?1 and o.state != 'unpaid'");
            query.setParameter(1, userName);
            return query.list();
        } else {
            /* Return all orders */
            Query query = session.createQuery(
                    "select o.userName, o.createDate, o.state, o.id, " +
                            "b.isbn,b.name,b.price,oi.quantity " +
                            "from  Order as o, OrderItem as oi, Book as b " +
                            "where o.id=oi.orderId and oi.bookId=b.id and o.userName=?1 ");
            query.setParameter(1, userName);
            return query.list();
        }
    }
    private List findBetweenDate(Timestamp begin, Timestamp end) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery(
                "select o.userName, o.createDate, o.state, o.id, " +
                        "b.isbn,b.name,b.price,oi.quantity " +
                        "from  Order as o, OrderItem as oi, Book as b " +
                        "where o.id=oi.orderId and oi.bookId=b.id and o.createDate between ?1 and ?2");
        query.setParameter(1, begin);
        query.setParameter(2, end);
        return query.list();
    }
    private List findCartByUser(String userName) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery(
                "select o.userName, o.createDate, o.state, o.id, " +
                        "b.isbn,b.name,b.price,b.stock, oi.quantity " +
                        "from  Order as o, OrderItem as oi, Book as b " +
                        "where o.id=oi.orderId and oi.bookId=b.id and o.userName = ?1 and o.state='unpaid'");
        query.setParameter(1, userName);
        return query.list();
    }
    private Order findCart(String userName) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery(
                "from Order o where o.userName = ?1 and o.state='unpaid'");
        query.setParameter(1, userName);
        List<Order> orders = query.list();
        if (orders.size() == 0) {
            return null;
        } else return orders.get(0);
    }
}
