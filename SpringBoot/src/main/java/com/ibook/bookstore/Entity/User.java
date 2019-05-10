package com.ibook.bookstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "users", schema = "bookstore", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_name")
    private String name;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "passwd")
    private String password;

    @Basic
    @Column(name = "avatar")
    private String avatar;

    @Basic
    @Column(name = "state")
    private boolean state;

    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

    @Basic
    @Column(name = "modify_date")
    private Timestamp modifyDate;

    @ManyToMany
    @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "user_name"),
        inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> likedBooks;

    @OneToMany(targetEntity = Address.class, mappedBy = "userName")
    private Set<Address> addresses;
}
