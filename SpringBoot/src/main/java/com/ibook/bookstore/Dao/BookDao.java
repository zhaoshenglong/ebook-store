package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Book;

public interface BookDao {
    Book findOne(String bookId);
}
