package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findOne(String id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> findAllByTag(String tag, Pageable pageable){
        Book book = new Book();
        book.setTag(tag);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Book> example = Example.of(book, matcher);
        return bookRepository.findAll(example, pageable);
    }

    @Override
    public Page<Book> findByIsbnNameAuthorLike(String isbn, String name, String author, Pageable pageable){
        Book book = new Book();
        book.setIsbn(isbn);
        book.setName(name);
        book.setAuthor(author);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("author", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("isbn", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Book> example = Example.of(book, matcher);
        return bookRepository.findAll(example, pageable);
    }

    @Override
    public Book updateIsbn(Book book, String isbn) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public Book updateName(Book book, String name){
        book.setName(name);
        return bookRepository.save(book);
    }
    @Override
    public Book updateAuthor(Book book, String author){
        book.setName(author);
        return bookRepository.save(book);
    }
    @Override
    public Book updateAuthorInfo(Book book, String authorInfo){
        book.setName(authorInfo);
        return bookRepository.save(book);
    }

    @Override
    public Book updateContent(Book book, String content){
        book.setName(content);
        return bookRepository.save(book);
    }

    @Override
    public Book updatePrice(Book book, double price) {
        book.setPrice(price);
        return bookRepository.save(book);
    }

    @Override
    public Book updateStock(Book book, int stock) {
        book.setStock(stock);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Book book) {
        book.setDeleted(false);
        bookRepository.save(book);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
