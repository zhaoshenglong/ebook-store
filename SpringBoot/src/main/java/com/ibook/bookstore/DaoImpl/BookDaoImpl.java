package com.ibook.bookstore.DaoImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Repository.BookRepository;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.eclipse.jetty.util.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private final String all_books_key = "all_books";

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Book findOne(String id) {
        ValueOperations ops = redisTemplate.opsForValue();
        Object value = ops.get(id);
        if (value == null) {
            Book b = bookRepository.getOne(id);
            if (b != null) {
                ops.set(id, b);
                return b;
            } else return null;
        } else {
            return (Book)value;
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Book> findAllNotDeleted(Pageable pageable) {
        ListOperations listOps = redisTemplate.opsForList();
        List<Book> totalBooks = listOps.range(all_books_key, 0, -1);
        if (totalBooks.size() == 0) {
            totalBooks = bookRepository.findAll();
            listOps.rightPushAll(all_books_key, totalBooks);
        }
        ArrayList<Book> targetBooks = new ArrayList<>();
        for (Book b : totalBooks) {
            if (!b.isDeleted()) {
                targetBooks.add(b);
            }
        }

        int start = (int)pageable.getOffset(),
                end = start + pageable.getPageSize();
        end = Math.min(end, targetBooks.size());
        return new PageImpl<>(targetBooks.subList(start, end), pageable, targetBooks.size());
    }

    /* For simplicity, ignore tag related apis */
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
    public Page<Book> findLike(String pattern, Pageable pageable, boolean includeDeleteBooks){
        ListOperations listOps = redisTemplate.opsForList();
        List<Book> totalBooks = listOps.range(all_books_key, 0, -1);
        if (totalBooks.size() == 0) {
            totalBooks = bookRepository.findAll();
            listOps.rightPushAll(all_books_key, totalBooks);
        }

        final SolrClient client = getSolrClient();
        String queryString = String.format("isbn:*%s* OR name:*%s* OR author:*%s* or author_info:*%s* OR content:*%s*",
            pattern, pattern, pattern, pattern, pattern);

        final SolrQuery query = new SolrQuery(queryString);

        QueryResponse response;
        SolrDocumentList documents;
        ArrayList<Book> targetBooks = new ArrayList<>();

        try {
            response = client.query("bookstore", query);
            documents = response.getResults();
            for (SolrDocument document: documents) {
                for (Book b: totalBooks) {
                    if (b.getId().equals(document.getFirstValue("id"))) {
                        if (includeDeleteBooks || !b.isDeleted()) {
                            targetBooks.add(b);
                        }
                    }
                }
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }

        int start = (int)pageable.getOffset(),
                end = start + pageable.getPageSize();
        end = Math.min(end, targetBooks.size());
        return new PageImpl<>(targetBooks.subList(start, end), pageable, targetBooks.size());
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Book> findAllIncludeDeleted(Pageable pageable) {
        ListOperations listOps = redisTemplate.opsForList();
        List totalBooks = listOps.range(all_books_key, 0, -1);
        if (totalBooks.size() == 0) {
            totalBooks = bookRepository.findAll();
            listOps.rightPushAll(all_books_key, totalBooks);
        }

        int start = (int)pageable.getOffset(),
                end = start + pageable.getPageSize();
        end = Math.min(end, totalBooks.size());
        return new PageImpl<>(totalBooks.subList(start, end), pageable, totalBooks.size());
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List findAll() {
        ListOperations listOps = redisTemplate.opsForList();
        if (listOps.size(all_books_key) == 0) {
            listOps.rightPushAll(all_books_key, bookRepository.findAll());
        }
        return listOps.range(all_books_key, 0, -1);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteBook(Book book) {
        book.setDeleted(true);
        ValueOperations ops = redisTemplate.opsForValue();
        ListOperations listOps = redisTemplate.opsForList();
        ArrayList<Book> books = (ArrayList<Book>) listOps.range(all_books_key, 0, -1);
        if (books != null) {
            int idx = 0;
            for (Book b : books) {
                if (book.getId().equals(book.getId())) {
                    listOps.set(all_books_key, idx, book);
                }
                idx++;
            }
        }
        ops.set(book.getId(), book);
        bookRepository.save(book);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Book saveBook(Book book) {
        book = bookRepository.save(book);
        System.out.println("************************************************************************" + book.getId());
        ValueOperations ops = redisTemplate.opsForValue();
        ListOperations listOps = redisTemplate.opsForList();
        ops.set(book.getId(), book);
        listOps.rightPush(all_books_key, book);

        final SolrClient solrClient = getSolrClient();
        try {
            solrClient.addBean("bookstore", book);
            System.out.println("It is all right");
            solrClient.commit("bookstore");
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
        return book;
    }

    static SolrClient getSolrClient() {
        final String solrUrl = "http://localhost:8983/solr";
        return new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }
}
