package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao {
    /* Query methods */
    Book findOne(String bookId);
    Page<Book> findAllNotDeleted(Pageable pageable);
    Page<Book> findAllByTagNotDeleted(String tag, Pageable pageable);
    Page<Book> findByIsbnNameAuthorLikeNotDeleted(String isbn, String name, String author, Pageable pageable);
    Page<Book> findAllIncludeDeleted(Pageable pageable);
    List findAll();
    Page<Book> findAllByIsbnNameAuthorLikeIncludeDeleted(String isbn, String name, String author, Pageable pageable);

    /* Delete methods */
    void deleteBook(Book book);

    /* Create methods */
    Book saveBook(Book book);
}
