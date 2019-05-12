package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookDao {
    /* Query methods */
    Book findOne(String bookId);
    Page<Book> findAll(Pageable pageable);
    Page<Book> findAllByTag(String tag, Pageable pageable);
    Page<Book> findByIsbnNameAuthorLike(String isbn, String name, String author, Pageable pageable);

    /* Delete methods */
    void deleteBook(Book book);

    /* Create methods */
    Book saveBook(Book book);
}
