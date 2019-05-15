package com.ibook.bookstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "users", schema = "bookstore", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class User {
    public interface userSimpleView{};
    public interface userDetailView{};

    @JsonView(userSimpleView.class)
    @Id
    @Column(name = "user_name")
    private String name;

    @JsonView(userSimpleView.class)
    @Basic
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Basic
    @Column(name = "passwd")
    private String password;

    @JsonView(userSimpleView.class)
    @Basic
    @Column(name = "avatar")
    private String avatar;

    // true -> activated false -> forbidden
    @JsonIgnore
    @Basic
    @Column(name = "state")
    private boolean state;

    @JsonIgnore
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

    @JsonIgnore
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

