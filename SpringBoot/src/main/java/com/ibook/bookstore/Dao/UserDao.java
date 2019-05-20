package com.ibook.bookstore.Dao;

import com.google.common.base.Optional;
import com.ibook.bookstore.Entity.Address;
import com.ibook.bookstore.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDao {
    /* Query methods */
    User findOne(String name);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);

    /* Create methods */
    User saveUser(User user);

    /* Delete methods */
    void deleteUser(String name);

    /* Update methods */

    User AddAddress(Address address, User user);
}
