package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.UserDetail;

import java.util.Optional;

public interface UserDetailDao {
    UserDetail findOne(String name);

    UserDetail saveOne(UserDetail userDetail);
}
