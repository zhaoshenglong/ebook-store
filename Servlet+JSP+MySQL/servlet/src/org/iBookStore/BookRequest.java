package org.iBookStore;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/bookRequest")
public class BookRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson g = new Gson();
        try {
            String js;
            ArrayList<Book> bookList = new ArrayList<Book>();
            for (int i = 0; i < 4; i++) {
                Book book= new Book("小王子"," [法] 圣埃克苏佩里","9787020042494",
                        "https://img3.doubanio.com/view/subject/l/public/s1103152.jpg",22.00);
                bookList.add(book);
            }
            js = g.toJson(bookList);
            out.print(js);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
