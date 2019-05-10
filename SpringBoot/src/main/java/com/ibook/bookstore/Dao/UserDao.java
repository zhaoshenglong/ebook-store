package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Address;
import com.ibook.bookstore.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDao {
    /* Query methods */
    User findOne(String name);
    User findByName(String name);
    Page<User> findAll(Pageable pageable);

    /* Create methods */
    User createUser(User user);

    /* Delete methods */
    void deleteUser(User user);

    /* Update methods */

    User updatePassword(String password, User user);
    User updateState(boolean state, User user);
    User AddAddress(Address address, User user);
}
