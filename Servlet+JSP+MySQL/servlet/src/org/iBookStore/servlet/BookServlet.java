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
import org.hibernate.Transaction;
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
        Gson gson = new Gson();
        try {
            String bookName = request.getQueryString().split("=")[1];
            if (bookName.equals("all")){
                List<Book> books;
                books = fetchAll();
                out.print(gson.toJson(books));
                out.flush();
            }
            else {
                Book book = fetchOne(bookName);
                out.print(gson.toJson(book));
            }
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            out.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Set response header */
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("UTF-8");

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        /* Begin transaction */
        PrintWriter out = response.getWriter();
        try {


        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /* Set response header */
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

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
        System.out.println(jb.toString());
        /* Begin transaction */
        PrintWriter out = response.getWriter();

    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /* Set response header */
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

    }
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
    }
    /* Get functions */
    private  List<Book> fetchAll () {
        List<Book> books = HibernateUtil.getSessionFactory()
                .getCurrentSession().createQuery("from Book").list();
        if (books.size() > 0) {
            return books;
        } else return null;
    }
    private  Book fetchOne (String qs) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from Book where isbn = ?1");
        query.setParameter(1, qs);
        List<Book> books = query.list();
        if (books.size() > 0) {
            return books.get(0);
        } else return null;
    }
    private void fetchLikePattern(String qs) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from Book where isbn = ?1");
        query.setParameter(1, qs);
    }
    private boolean addBook(Book book) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery("from Book where isbn= ?1");
        query.setParameter(1, book.getIsbn());
        if (query.list().size() > 0)
            return false;
        else {
            session.save(book);
            transaction.commit();
            return true;
        }
    }
    private boolean modifyBook(Book book) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery("from Book where isbn=?1");
        query.setParameter(1, book.getIsbn());
        if (query.list().size() > 0) {
            return false;
        } else {
            session.update(book);
            transaction.commit();
            return true;
        }
    }
    private boolean deleteBook(String isbn) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction = session.getTransaction();
        Query query = session.createQuery("from Book where isbn = ?1");
        query.setParameter(1, isbn);
        List<Book> books = query.list();
        try {
            Book book = books.get(0);
            session.delete(book);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

}
