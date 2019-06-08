package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Dao.BookDetailDao;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Entity.BookDetail;
import com.ibook.bookstore.Repository.BookDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class BookDetailDaoImpl implements BookDetailDao {
    @Autowired
    private BookDetailRepository bookDetailRepository;

    @Override
    public BookDetail findOne(String id) {
        return bookDetailRepository.findByBookId(id);
    }

    @Override
    public void saveOne(BookDetail bookDetail) {
        bookDetailRepository.save(bookDetail);
    }
}
