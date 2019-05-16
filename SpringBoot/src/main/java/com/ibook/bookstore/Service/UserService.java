package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.Address;
import com.ibook.bookstore.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {
    User findUserByName(String name);
    User findUserByEmail(String email);
    Page<User> findUserByPage(int page, int size);

    User createUser(User user);

    User likeBook(String name, String id);

    @Transactional
    void deleteUser(String name);

    User updateUser(Map<String, String> data);

    User addAddress(String name, Address address);

    User updateAddress(Map<String, String> addressData);

    void deleteAddress(String id);

    Boolean nameCanBeRegistered(String name);
    Boolean emailCanBeRegistered(String email);

    Map<String, String> getStatus(HttpSession session, HttpServletResponse response);
}
