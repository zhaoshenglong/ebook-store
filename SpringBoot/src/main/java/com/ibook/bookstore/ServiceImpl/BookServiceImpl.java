package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public Book findBookById(String id) {
        return bookDao.findOne(id);
    }

    @Override
    public Page<Book> findBookByPage(int page, int size, String user) {
        Pageable pageable = PageRequest.of(page, size);
        if (user.equals("admin")) {
            return bookDao.findAllIncludeDeleted(pageable);
        } else {
            return bookDao.findAllNotDeleted(pageable);
        }
    }

    @Override
    public Page<Book> findBookByTagPage(String tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookDao.findAllByTagNotDeleted(tag, pageable);
    }

    @Override
    public Page<Book> findAllLike(String pattern, int page, int size, String user) {
        Pageable pageable = PageRequest.of(page, size);
        if (user.equals("admin")) {
            return bookDao.findAllByIsbnNameAuthorLikeIncludeDeleted(pattern, pattern, pattern, pageable);
        } else {
            return bookDao.findByIsbnNameAuthorLikeNotDeleted(pattern, pattern, pattern, pageable);
        }
    }

    /*
     *   Create a new book, required name, isbn, price and author
     *   Corner case:
     *      1. multiple book with same name and isbn and everything except id(id uuid auto generated)
     *      2. ???
     */
    @Override
    public Book createBook(Book book) {
        book.setId(UUID.randomUUID().toString());
        book.setImg(book.getImg() == null ? "http://localhost:8080/img?kind=book&name=default":book.getImg());
        book.setLiked(0);
        book.setCreateDate(new Timestamp(System.currentTimeMillis()));
        book.setDeleted(false);
        return bookDao.saveBook(book);
    }

    /* Soft delete book
     *  What if the id is not valid ?
     *  What if the request is not authorized?
     * */
    @Override
    public void deleteBook(String id) {
        Book book = bookDao.findOne(id);
        bookDao.deleteBook(book);
    }

    /*
     * Update book information
     * What if the id is not valid?
     *
     * */
    @Override
    public Book updateBook(Map<String, String> data) {
        String id = data.get("id");
        Book book = bookDao.findOne(id);
        String isbn = data.get("isbn"), name = data.get("name"),
               author = data.get("author"), tag = data.get("tag"),
               contentInfo = data.get("contentInfo"), authorInfo = data.get("authorInfo"),
               price = data.get("price"), stock = data.get("stock");
        if (isbn != null)
            book.setIsbn(isbn);
        if (name != null)
            book.setName(name);
        if (author != null)
            book.setAuthor(author);
        if (tag != null)
            book.setTag(tag);
        if (contentInfo != null)
            book.setContentInfo(contentInfo);
        if (authorInfo != null)
            book.setAuthorInfo(authorInfo);
        if (price != null)
            book.setPrice(Double.parseDouble(price));
        if (stock != null)
            book.setStock(Integer.parseInt(stock));
        book.setModifyDate(new Timestamp(System.currentTimeMillis()));
        return bookDao.saveBook(book);
    }


}
