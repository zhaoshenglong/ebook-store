package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.User;

public interface UserService {
    User findUserByName(String name);
}
