package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.Person;

import java.util.Set;

public interface PersonService {
    Set<Person> findFollowers(String name);

    Set<Person> findFollowings(String name);

    Person follow(String userName, String targetName);

    Person unFollow(String userName, String targetName);
}
