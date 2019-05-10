package com.ibook.bookstore.Repository;


import com.ibook.bookstore.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {}
