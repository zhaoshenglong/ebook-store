package com.ibook.bookstore.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
@NoArgsConstructor
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "FOLLOWINGS")
    private Set<Person> followings;

    @Relationship(type = "FOLLOWERS", direction = Relationship.INCOMING)
    private Set<Person> followers;
}
