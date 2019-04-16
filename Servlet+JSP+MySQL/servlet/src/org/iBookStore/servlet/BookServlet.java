package org.iBookStore.servlet;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.iBookStore.HibernateUtil;
import org.iBookStore.entity.Book;
import javax.servlet.annotation.WebServlet;


@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Set response header */
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        /* Begin transaction */
        PrintWriter out = response.getWriter();
        try {
            String qs = request.getQueryString().split("=")[1];
            if (qs.equals("all"))
                fetchAll(out);
            else {
                fetchOne(qs, out);
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
        /* Set response header */
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        /* Begin transaction */
        PrintWriter out = response.getWriter();
        try {
            String qs = request.getQueryString().split("=")[1];
            if (qs.equals("all"))
                fetchAll(out);
            else {
                fetchOne(qs, out);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /* Get functions */
    private  void fetchAll (PrintWriter out) {
        List<Book> books = HibernateUtil.getSessionFactory()
                .getCurrentSession().createQuery("from Book").list();
        if (books.size() > 0) {
            Gson g = new Gson();
            String js = g.toJson(books);
            out.print(js);
        }
    }
    private  void fetchOne (String qs, PrintWriter out) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from Book where isbn = ?1");
        query.setParameter(1, qs);
        List<Book> books = query.list();
        if (books.size() > 0) {
            Gson g = new Gson();
            String js = g.toJson(books);
            out.print(js);
        }
    }
    private void fetchLikePattern(String qs, PrintWriter out) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from Book where isbn = ?1");
        query.setParameter(1, qs);

    }
}
