package com.ibook.bookstore.Repository;

import com.ibook.bookstore.Entity.BookDetail;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookDetailRepository extends MongoRepository<BookDetail, String> {
    @Query("{'bookId': ?0 }")
    BookDetail findByBookId(String id);
}
