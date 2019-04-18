package org.iBookStore.servlet.utility;

import org.iBookStore.entity.Book;
import org.iBookStore.entity.User;
import javax.servlet.http.HttpServletRequest;

public class EntityUtility {
    public static void setBook(Book book,  HttpServletRequest request) throws Exception {
        if (request.getParameter("isbn") != null) {
            book.setIsbn(request.getParameter("isbn"));
        }
        if (request.getParameter("name") != null) {
            String name = StringUtility.encodeUTF8(request.getParameter("name"));
            book.setName(name);
        }
        if (request.getParameter("author") != null) {
            String author = StringUtility.encodeUTF8(request.getParameter("author"));
            book.setAuthor(author);
        }
        if (request.getParameter("img") != null) {
            book.setImg(request.getParameter("img"));
        }
        if (request.getParameter("price") != null) {
            book.setPrice(Double.parseDouble(request.getParameter("price")));
        }
        if (request.getParameter("stock") != null) {
            book.setStock(Integer.parseInt(request.getParameter("isbn")));
        }
        if (request.getParameter("authorInfo") != null) {
            String authorInfo = StringUtility.encodeUTF8(request.getParameter("authorInfo"));
            book.setAuthorInfo(authorInfo);
        }
        if (request.getParameter("contentInfo") != null) {
            String contentInfo = StringUtility.encodeUTF8(request.getParameter("contentInfo"));
            book.setContentInfo(contentInfo);
        }
    }
    public static void setUser(User user, HttpServletRequest request) {
        if (request.getParameter("email") != null) {
            user.setEmail(request.getParameter("email"));
        }
        if (request.getParameter("password") != null) {
            user.setPassword(request.getParameter("password"));
        }
        if (request.getParameter("briefAddr") != null) {
            user.setBriefAddr(request.getParameter("briefAddr"));
        }
        if (request.getParameter("detailAddr") != null) {
            user.setDetailAddr(request.getParameter("detailAddr"));
        }
        if (request.getParameter("state") != null) {
            user.setState(request.getParameter("State"));
        }
    }
}
