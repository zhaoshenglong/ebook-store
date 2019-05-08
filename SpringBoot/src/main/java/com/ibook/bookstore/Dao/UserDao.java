package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.User;

public interface UserDao {
    User findOne(String name);
}
