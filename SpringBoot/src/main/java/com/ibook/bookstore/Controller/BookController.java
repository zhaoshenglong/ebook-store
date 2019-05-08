package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable("id") String id) {
        return bookService.findBookById(id);
    }
}
