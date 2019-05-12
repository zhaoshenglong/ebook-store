package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.User;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface UserService {
    User findUserByName(String name);
    User findUserByEmail(String email);
    Page<User> findUserByPage(int page, int size);

    User createUser(User user);

    void deleteUser(String name);

    User updateUser(Map<String, String> data);

    User addAddress()
}
