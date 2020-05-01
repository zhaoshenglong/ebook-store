package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Person;

public interface PersonDao {
    Person findOne(String name);

    /* Create methods */
    Person savePerson(Person person);
}
