package com.ibook.bookstore.Repository;

import com.google.common.base.Optional;
import com.ibook.bookstore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    void deleteByName(String name);
}
