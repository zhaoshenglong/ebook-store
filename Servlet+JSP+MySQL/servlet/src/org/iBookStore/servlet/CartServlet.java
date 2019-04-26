package org.iBookStore.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.iBookStore.HibernateUtil;
import org.iBookStore.entity.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

import static org.iBookStore.servlet.utility.ServletUtility.setCORS;

@WebServlet("/cartServlet")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORS(response);
        response.setContentType("application/json");
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        PrintWriter out = response.getWriter();
        try{
            HttpSession httpSession = request.getSession(false);
            Order cartOrder = (Order) httpSession.getAttribute("cart");
            ReturnJson rs = new ReturnJson();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            if (cartOrder == null) {
                rs.setMsg("Need login first");
                out.print(gson.toJson(rs));
                return;
            } else {
                List res = findCartDetail(cartOrder.getId());
                out.print(gson.toJson(res));
            }
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            out.flush();
            out.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORS(response);
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        ReturnJson rs = new ReturnJson();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        HttpSession httpSession = request.getSession();
        Order cart;
        if ( (cart = (Order)httpSession.getAttribute("cart")) == null) {
            rs.setMsg("Need login first");
            out.print(gson.toJson(rs));
            return;
        }

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        /* Get the orderItem from request */
        OrderItem orderItem = gson.fromJson(request.getParameter("orderItem"), OrderItem.class);
        orderItem.setOrder(cart);
        try {
            /* Add to cart */
           if (action.equals("add")) {
                if (cart.getOrderItemList() == null) {
                    cart.setOrderItemList( new HashSet<>());
                }
                int exist = 0;
                Iterator<OrderItem> itemIterator = cart.getOrderItemList().iterator();
                while (itemIterator.hasNext()) {
                    OrderItem oi = itemIterator.next();
                    if (oi.getBookId().equals(orderItem.getBookId())) {
                        oi.setQuantity(oi.getQuantity() + orderItem.getQuantity());
                        exist = 1;
                        break;
                    }
                }
                if (exist==0)cart.getOrderItemList().add(orderItem);
                cart.setCreateDate(new Timestamp(System.currentTimeMillis()));
                HibernateUtil.getSessionFactory().getCurrentSession().update(cart);
                rs.setMsg("Add ok");

            }

            else if (action.equals("delete")) {
                Iterator<OrderItem> itemIterator = cart.getOrderItemList().iterator();
                while(itemIterator.hasNext()) {
                    OrderItem oi = itemIterator.next();
                    if (oi.getBookId().equals(orderItem.getBookId())){
                        oi = HibernateUtil.getSessionFactory().getCurrentSession().get(OrderItem.class, oi.getId());
                        HibernateUtil.getSessionFactory().getCurrentSession().delete(oi);
                        itemIterator.remove();
                        break;
                    }
                }
                rs.setMsg("Delete ok");
            }

            else if (action.equals("modify")) {
                Iterator<OrderItem> itemIterator = cart.getOrderItemList().iterator();
                while (itemIterator.hasNext()) {
                    OrderItem oi = itemIterator.next();
                    if (oi.getBookId().equals(orderItem.getBookId())) {
                        oi.setQuantity(orderItem.getQuantity());
                        break;
                    }
                }
                cart.setCreateDate(new Timestamp(System.currentTimeMillis()));
                HibernateUtil.getSessionFactory().getCurrentSession().update(cart);
                rs.setMsg("Modify ok");
            }
            out.print(gson.toJson(rs));
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch ( Exception e) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
            rs.setMsg("Option failed");
        } finally {
            out.flush();
            out.close();
        }

    }
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORS(response);
    }


    private List<OrderItemDetail> findCartDetail(String id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        try{
            List result;
            Query query = session.createQuery("select b.id, b.price,b.name,b.isbn,b.author,b.img, oi.quantity, b.stock from OrderItem as oi, Book as b where oi.bookId = b.id and oi.order.id = ?1");
            query.setParameter(1, id);
            result = query.list();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
