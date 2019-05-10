package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookDao {
    /* Query methods */
    Book findOne(String bookId);
    Page<Book> findAll(Pageable pageable);
    Page<Book> findAllByTag(String category, Pageable pageable);
    Page<Book> findByIsbnNameAuthorLike(String isbn, String name, String author, Pageable pageable);
    /* Update methods */
    Book updateIsbn(Book book, String isbn);
    Book updatePrice(Book book, double price);
    Book updateName(Book book, String name);
    Book updateAuthor(Book book, String author);
    Book updateAuthorInfo(Book book, String authorInfo);
    Book updateContent(Book book, String content);
    Book updateStock(Book book, int stock);
    /* Delete methods */
    void deleteBook(Book book);

    /* Create methods */
    Book saveBook(Book book);
}
