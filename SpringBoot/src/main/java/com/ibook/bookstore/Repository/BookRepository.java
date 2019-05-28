package com.ibook.bookstore.Repository;


import com.ibook.bookstore.Entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
    Page<Book> findAllByAuthorContainsOrNameContainsOrIsbnContains(String author, String name, String isbn, Pageable pageable);
    Page<Book> findAllByAuthorContainsAndDeletedIsFalseOrNameContainsAndDeletedIsFalseOrIsbnContainsAndDeletedIsFalse(
            String author, String name, String isbn, Pageable pageable);
    Page<Book> findAllByDeletedIsFalse(Pageable pageable);
}
