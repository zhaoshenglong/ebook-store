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

        /* There some problem with Example
         * Reason may be for that withIncludeNullValues must set before withIgnorePaths,
         * or it may ignore the null String / Object and thus has no effect
         * */

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("price", "stock", "deleted", "liked", "id",  "name",
                        "author", "isbn", "authorInfo", "contentInfo", "img", "createDate", "modifyDate")
                .withIncludeNullValues();

        Example<Book> example = Example.of(book, matcher);
        return bookRepository.findAll(example, pageable);
    }

    @Override
    public Page<Book> findByIsbnNameAuthorLike(String isbn, String name, String author, Pageable pageable){

        return bookRepository.findAllByAuthorContainsOrNameContainsOrIsbnContains(author, name, isbn, pageable);
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
