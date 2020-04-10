package com.ibook.bookstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "books", schema = "bookstore")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Book implements Serializable {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue( generator = "jpa-uuid")
    @Field
    private String id;

    @Basic
    @Column(name = "book_name")
    @Field
    private String name;

    @Basic
    @Column(name = "author")
    @Field
    private String author;

    @Basic
    @Column(name = "book_isbn")
    @Field
    private String isbn;

    @Basic
    @Column(name = "price")
    private double  price;

    @Basic
    @Column(name = "tag")
    private String tag;

    @Basic
    @Column(name = "author_info")
    @Field("author_info")
    private String authorInfo;

    @Basic
    @Column(name = "content")
    @Field("content")
    private String contentInfo;

    @Basic
    @Column(name = "img")
    private String img;

    @Basic
    @Column(name = "stock")
    private int stock;

    @Basic
    @Column(name = "sale")
    private int sale;

    @JsonIgnore
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

    @JsonIgnore
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
