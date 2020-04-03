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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Book findOne(String id) {
        return bookRepository.getOne(id);
    }

    @Override

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Book> findAllNotDeleted(Pageable pageable) {
        return bookRepository.findAllByDeletedIsFalse(pageable);
    }

    @Override

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Book> findAllByTagNotDeleted(String tag, Pageable pageable){
        Book book = new Book();
        book.setTag(tag);
        book.setDeleted(false);
        /* There some problem with Example
         * Reason may be for that withIncludeNullValues must set before withIgnorePaths,
         * or it may ignore the null String / Object and thus has no effect
         * */
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("price", "stock", "liked", "id",  "name",
                        "author", "isbn", "authorInfo", "contentInfo", "img", "createDate", "modifyDate")
                .withIncludeNullValues();

        Example<Book> example = Example.of(book, matcher);
        return bookRepository.findAll(example, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Book> findByIsbnNameAuthorLikeNotDeleted(String isbn, String name, String author, Pageable pageable){

        return bookRepository.findAllByAuthorContainsOrNameContainsOrIsbnContains(author, name, isbn, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Book> findAllIncludeDeleted(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Book> findAllByIsbnNameAuthorLikeIncludeDeleted(String isbn, String name, String author, Pageable pageable) {
        return bookRepository.findAllByAuthorContainsOrNameContainsOrIsbnContains(author,name,isbn,pageable);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteBook(Book book) {
        book.setDeleted(true);
        bookRepository.save(book);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
