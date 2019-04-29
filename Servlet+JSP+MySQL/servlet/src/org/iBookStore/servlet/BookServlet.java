package org.iBookStore.servlet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.iBookStore.HibernateUtil;
import org.iBookStore.entity.Book;
import org.iBookStore.entity.ReturnJson;

import javax.servlet.annotation.WebServlet;
import org.iBookStore.servlet.utility.*;
import static org.iBookStore.servlet.utility.ServletUtility.*;
@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Set response header */
        setCORS(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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

        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        try {
            String action = request.getQueryString().split("=")[0];
            if (action.equals("id")) {
                String bookId = request.getQueryString().split("=")[1];
                if (bookId.equals("all")){
                    List<Book> books;
                    books = fetchAll();
                    out.print(gson.toJson(books));
                } else if (bookId.equals("together")) {
                    String data = StringUtility.getReaderContent(request);
                    List<String> books = gson.fromJson(data, new TypeToken<List<String>>(){}.getType());
                    List<Book> bookList = fetchTogether((String[])books.toArray());
                    out.print(gson.toJson(bookList));
                } else {
                    Gson detailGson = new Gson();
                    Book book = fetchOne(bookId);
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
                response.setStatus(404);
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
        /* Begin transaction */
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        ReturnJson rs = new ReturnJson();
        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        try {
            Book book = HibernateUtil.getSessionFactory()
                    .getCurrentSession().get(Book.class, request.getParameter("id"));
            if (book != null) {
                EntityUtility.setBook(book, request);
                HibernateUtil.getSessionFactory().getCurrentSession().update(book);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                rs.setMsg("Update ok");
                out.print(gson.toJson(rs));
            } else {
                rs.setMsg("Id not found");
                response.setStatus(404);
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
        /* Begin transaction */
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        ReturnJson rs = new ReturnJson();
        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        try {
            String data = StringUtility.getReaderContent(request);
            Book book = gson.fromJson(data, Book.class);
            book.setImg("default");
            book.setTag("Story");
            HibernateUtil.getSessionFactory().getCurrentSession().save(book);
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
            rs.setMsg("Update ok");
            out.print(gson.toJson(rs));
        }catch (Exception e) {
            response.setStatus(404);
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
        /* Begin transaction */
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        ReturnJson rs = new ReturnJson();
        /* Hibernate transaction initialize */
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        try {
            Book book = HibernateUtil.getSessionFactory().getCurrentSession().get(Book.class, request.getParameter("id"));
            if (book != null){
                HibernateUtil.getSessionFactory().getCurrentSession().delete(book);
                HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                rs.setMsg("Delete ok");
                out.print(gson.toJson(rs));
            } else {
                response.setStatus(404);
                rs.setMsg("Delete failed");
                out.print(gson.toJson(rs));
            }
        }catch (Exception e) {
            response.setStatus(500);
            rs.setMsg("Delete failed");
            out.print(gson.toJson(rs));
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
    /* For ServletUtility at developing stage */
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        setCORS(response);
        response.setStatus(200);
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
        List<Book> books;
        Query query = session.createQuery("from Book where tag = ?1");
        query.setParameter(1, tag);
        books = query.list();
        return books;
    }

    private List<Book> fetchLatest() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        List<Book> books;
        return null;
    }
    private List<Book> fetchHottest() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        List<Book> books;
        Query hottest = session.createQuery("select count(bookId) as c from OrderItem group by bookId order by c");
        List<Integer> statistic = hottest.list();
        int hot_10 = 0;
        if (statistic.size() > 10)
            hot_10 = statistic.get(10);
        else return session.createQuery("from Book where id in (select bookId from OrderItem )").list();

        Query query = session.createQuery("from Book where id in (select bookId from OrderItem where  count(bookId) > ?1)");
        query.setParameter(1, hot_10);
        return query.list();
    }
    private List<Book> fetchTogether(String[] bookIdList) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession().getSession();
        Transaction transaction =  session.getTransaction();
        List<Book> books = new ArrayList<>();
        Query query = session.createQuery("from Book where id = ?1");
        for (String id : bookIdList) {
            query.setParameter(1, id);
            books.addAll(query.list());
        }
        return books;
    }
}
