package com.ibook.bookstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name = "books", schema = "bookstore")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Book {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue( generator = "jpa-uuid")
    private String id;

    @Basic
    @Column(name = "book_name")
    private String name;

    @Basic
    @Column(name = "author")
    private String author;

    @Basic
    @Column(name = "book_isbn")
    private String isbn;

    @Basic
    @Column(name = "price")
    private double  price;

    @Basic
    @Column(name = "tag")
    private String tag;

    @Basic
    @Column(name = "author_info")
    private String authorInfo;

    @Basic
    @Column(name = "content")
    private String contentInfo;

    @Basic
    @Column(name = "img")
    private String img;

    @Basic
    @Column(name = "stock")
    private int stock;

    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

    @Basic
    @Column(name = "modify_date")
    private Timestamp modifyDate;

    @Basic
    @Column(name = "deleted")
    private boolean deleted;

    @Basic
    @Column(name = "liked")
    private int liked;
}
