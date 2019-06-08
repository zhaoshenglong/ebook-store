package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface BookService {
    Book findBookById(String id);

    /**
     * NOTE: not distinct user and admin here!
     */
    Page<Book> findBookByPage(int page, int size, String user);
    Page<Book> findBookByTagPage(String tag, int page, int size);
    Page<Book> findAllLike(String pattern, int page, int size, String user);

    Book createBook(Book book);

    void deleteBook(String id);

    Book updateBook(Map<String, String> data);
}
