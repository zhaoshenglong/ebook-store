package org.iBookStore;

import org.iBookStore.entity.Book;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    static final String dbUrl = "jdbc:mysql://localhost:3306/bookstore";
    static final String user = "dragon";
    static final String password = "HXC19970129";
    static Connection conn = null;
    static PreparedStatement fetchAllBook = null;
    static String fetchAllBookStat = "select * from books natural join tags";
    public void open() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            this.close();
        }
    }
    public ArrayList<Book> fetchAllBooks() {
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            fetchAllBook = conn.prepareStatement(fetchAllBookStat);
            ResultSet rs = fetchAllBook.executeQuery();
            while(rs.next()) {
                Book book = new Book();
                book.setIsbn(rs.getString(1));
                book.setName(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setPrice(rs.getDouble(4));
                book.setTag(rs.getString(5));
                book.setImg("http://localhost:8088/img?kind=book&name=" + book.getIsbn());
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public void close() {
        try{

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
