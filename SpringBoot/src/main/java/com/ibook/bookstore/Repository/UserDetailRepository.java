package com.ibook.bookstore.Repository;

import com.ibook.bookstore.Entity.UserDetail;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserDetailRepository extends MongoRepository<UserDetail, String> {
    @Query("{'name': ?0}")
    public UserDetail findByName(String name);
}
