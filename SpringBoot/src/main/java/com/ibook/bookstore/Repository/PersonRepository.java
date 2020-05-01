package com.ibook.bookstore.Repository;

import com.ibook.bookstore.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByName(String name);
}
