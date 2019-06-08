package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.BookDetail;

import java.util.List;

public interface BookDetailDao {
    BookDetail findOne(String id);

    void saveOne(BookDetail bookDetail);
}
