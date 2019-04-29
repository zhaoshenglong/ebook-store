package org.iBookStore.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
import static org.iBookStore.servlet.utility.ServletUtility.*;

@WebServlet ("/orderServlet")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        setCORS(response);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List orders;
        ReturnJson rs = new ReturnJson();
        HttpSession httpSession = request.getSession(false);
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
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
                    orders = findBetweenDate("admin", begin, end);
                    out.print(gson.toJson(orders));
                } else {
                    return;
                }
            } else if (role.equals("user")) {
                if (action.equals("findByUser")) {
                    String userName = (String)httpSession.getAttribute("name");
                    orders = findByUser(userName, 1);
                    out.print(gson.toJson(orders));
                } else if (action.equals("findBetween")) {
                    Timestamp begin = Timestamp.valueOf(request.getParameter("begin"));
                    Timestamp end = Timestamp.valueOf(request.getParameter("end"));
                    orders = findBetweenDate( (String)request.getSession(false).getAttribute("name"),begin, end);
                    out.print(gson.toJson(orders));
                }
            } else {
                response.setStatus(404);
                rs.setMsg("Role not found");
                out.print(gson.toJson(rs));
                return;
            }
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            response.setStatus(404);
            out.print(gson.toJson(rs));
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
        ReturnJson rs = new ReturnJson();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        HttpSession httpSession = request.getSession(false);
        PrintWriter out = response.getWriter();
        if (httpSession == null) {
            rs.setMsg("Need log in");
            response.setStatus(403);
            out.print(gson.toJson(rs));
            return;
        }
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        try{
            String userName = (String)httpSession.getAttribute("name");
            String readerContent = StringUtility.getReaderContent(request);
            Set<OrderItem> orderItemList = gson.fromJson(readerContent, new TypeToken<Set<OrderItem>>(){}.getType());
            Order order = new Order();
            order.setState("paid");
            order.setCreateDate(new Timestamp(System.currentTimeMillis()));
            order.setUserName(userName);
            order.setOrderItemList(orderItemList);
            updateStock(orderItemList);
            Order cart = (Order) httpSession.getAttribute("cart");
            clearCart(orderItemList, cart);
            HibernateUtil.getSessionFactory().getCurrentSession().update(cart);
            HibernateUtil.getSessionFactory().getCurrentSession().save(order);
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

        } catch (Exception e) {

            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            rs.setMsg("Post failed");
            response.setStatus(500);
            out.print(gson.toJson(rs));
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
        response.setStatus(200);
    }

    private List<Order> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery("from Order ");
        return query.list();
    }
    private List findByUser(String userName, int flag) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        if (flag == 1) {
            /* Return paid order */
            Query query = session.createQuery("select o.userName, o.createDate, o.id, b.id, b.img, b.author, b.isbn, b.name, b.price, b.tag, oi.quantity " +
                   "from Order as o, Book as b, OrderItem  as oi  where o.userName=?1 and o.state = 'paid' and o.id = oi.order.id and oi.bookId = b.id");
            query.setParameter(1, userName);
            return query.list();
        } else {
            /* Return all orders, including unpaid*/
            Query query = session.createQuery("from Order where userName=?1");
            query.setParameter(1, userName);
            return query.list();
        }
    }
    private List findBetweenDate(String name, Timestamp begin, Timestamp end) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("select o.userName, o.createDate, o.id, b.id, b.img, b.author, b.isbn, b.name, b.price, b.tag, oi.quantity " +
                "from Order as o, Book as b, OrderItem  as oi  where o.userName=?1 and o.state = 'paid' and o.id = oi.order.id and oi.bookId = b.id " +
                "and o.createDate between ?2 and ?3");
        query.setParameter(1, name);
        query.setParameter(2, begin);
        query.setParameter(3, end);
        return query.list();
    }
    private void updateStock(Set<OrderItem> orderItemSet) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        try{
            Iterator<OrderItem> itemIterator = orderItemSet.iterator();
            while(itemIterator.hasNext()) {
                OrderItem orderItem = itemIterator.next();
                Book book = session.get(Book.class, orderItem.getBookId());
                if (book.getStock() >= orderItem.getQuantity()) {
                    book.setStock(book.getStock() - orderItem.getQuantity());
                    session.update(book);
                } else {
                    throw new RuntimeException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void clearCart(Set<OrderItem> orderItemSet, Order cart) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        try {
            Iterator<OrderItem> itemIterator = cart.getOrderItemList().iterator();
            while(itemIterator.hasNext()) {
                OrderItem orderItem = itemIterator.next();
                Iterator<OrderItem> oit = orderItemSet.iterator();
                while(oit.hasNext()) {
                    OrderItem oi = oit.next();
                    if (oi.getBookId().equals(orderItem.getBookId())) {
                        itemIterator.remove();
                        session.delete(orderItem);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
