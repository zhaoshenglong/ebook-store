package com.ibook.bookstore.Repository;

import com.ibook.bookstore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
