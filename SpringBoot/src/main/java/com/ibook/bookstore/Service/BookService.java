package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.Book;

public interface BookService {
    Book findBookById(String id);
}
