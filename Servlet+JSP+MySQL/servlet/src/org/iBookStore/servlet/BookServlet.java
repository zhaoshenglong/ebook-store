package org.iBookStore.servlet;

import java.io.*;
import java.util.ArrayList;
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
import org.iBookStore.entity.Book;
import org.iBookStore.entity.ReturnJson;

import javax.servlet.annotation.WebServlet;
import org.iBookStore.servlet.utility.*;
import static org.iBookStore.servlet.utility.CORS.*;
@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Set response header */
        setCORS(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        /* Begin transaction */
        PrintWriter out = response.getWriter();
        /* Gson instance ignore details */
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getName().contains("Info");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create();
        try {
            String action = request.getQueryString().split("=")[0];
            if (action.equals("name")) {
                String bookName = request.getQueryString().split("=")[1];
                if (bookName.equals("all")){
                    List<Book> books;
                    books = fetchAll();
                    out.print(gson.toJson(books));
                }
                else {
                    Gson detailGson = new Gson();
                    Book book = fetchOne(bookName);
                    out.print(detailGson.toJson(book));
                }
            } else if (action.equals("tag")) {
                String tag = request.getQueryString().split("=")[1];
                List<Book> books;
                if (tag.equals("Latest")) {
                    books = fetchLatest();
                    out.print(gson.toJson(books));
                } else if (tag.equals("Hottest")) {
                    books = fetchHottest();
                    out.print(gson.toJson(books));
                } else {
                    /* Normal way, only query tags */
                    books = fetchTag(tag);
                    out.print(gson.toJson(books));
                }
            } else if (action.equals("pattern")) {
                String pattern = request.getQueryString().split("=")[1];
                String[] patterns =  pattern.split("%20");
                List<Book> books = new ArrayList<>();
                for (String p : patterns) {
                    books.addAll(fetchPattern(p));
                }
                out.print(gson.toJson(books));
            } else {
                ReturnJson rs = new ReturnJson();
                rs.setStatus("404");
                rs.setMsg("Unknown query " + action);
                out.print(gson.toJson(rs));
            }
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            out.flush();
            out.close();
        }
    }
    /* Update a book */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Set response header */
        setCORS(response);
        response.setCharacterEncoding("UTF-8");
        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        /* Begin transaction */
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        ReturnJson rs = new ReturnJson();
        try {
            Book book = HibernateUtil.getSessionFactory()
                    .getCurrentSession().get(Book.class, request.getParameter("id"));
            if (book != null) {
                EntityUtility.setBook(book, request);
                HibernateUtil.getSessionFactory().getCurrentSession().update(book);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                rs.setMsg("Update ok");
                rs.setStatus("200");
                out.print(gson.toJson(rs));
            } else {
                rs.setMsg("Id not found");
                rs.setStatus("404");
                out.print(gson.toJson(rs));
            }
        } catch(Exception e) {
            e.printStackTrace();
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            out.flush();
            out.close();
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /* Set response header */
        setCORS(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        /* Begin transaction */
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        ReturnJson rs = new ReturnJson();
        try {
            String data = StringUtility.getReaderContent(request);
            Book book = gson.fromJson(data, Book.class);
            book.setImg("default");
            book.setTag("Story");
            HibernateUtil.getSessionFactory().getCurrentSession().save(book);
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
            rs.setMsg("Update ok");
            rs.setStatus("200");
            out.print(gson.toJson(rs));
        }catch (Exception e) {
            rs.setStatus("404");
            rs.setMsg("Put failed");
            out.print(gson.toJson(rs));
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /* Set response header */
        setCORS(response);

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        /* Begin transaction */
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        ReturnJson rs = new ReturnJson();
        try {
            Book book = HibernateUtil.getSessionFactory().getCurrentSession().get(Book.class, request.getParameter("id"));
            if (book != null){
                HibernateUtil.getSessionFactory().getCurrentSession().delete(book);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                rs.setMsg("Delete ok");
                rs.setStatus("200");
                out.print(gson.toJson(rs));
            } else {
                rs.setStatus("404");
                rs.setMsg("Delete failed");
                out.print(gson.toJson(rs));
            }
        }catch (Exception e) {
            rs.setStatus("404");
            rs.setMsg("Delete failed");
            out.print(gson.toJson(rs));
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
    /* For CORS at developing stage */
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        setCORS(response);
    }

    /* Get functions */
    private  List<Book> fetchAll () {
        List<Book> books = HibernateUtil.getSessionFactory()
                .getCurrentSession().createQuery("from Book").list();
        if (books.size() > 0) {
            return books;
        } else return null;
    }
    private  Book fetchOne (String id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Query query = session.createQuery("from Book where id = ?1");
        query.setParameter(1, id);
        List<Book> books = query.list();
        if (books.size() > 0) {
            return books.get(0);
        } else return null;
    }
    private List<Book> fetchPattern(String pattern) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        List<Book> books;
        Query query = session.createQuery("from Book where name like ?1 or isbn like ?1 or author like ?1");
        query.setParameter(1, "%" + pattern + "%");
        books = query.list();
        return books;
    }

    private List<Book> fetchTag(String tag) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction =  session.getTransaction();
        List<Book> books;
        Query query = session.createQuery("from Book where tag = ?1");
        query.setParameter(1, tag);
        books = query.list();
        return books;
    }

    private List<Book> fetchLatest() {
        return null;
    }
    private List<Book> fetchHottest() {
        return null;
    }
}