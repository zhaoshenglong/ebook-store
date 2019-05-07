package com.ibook.bookstore.Entity;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;

@Entity
@Table(name = "books", schema = "bookstore", catalog = "")
public class Book {
    private String id;
    private String name;
    private String author;
    private String isbn;
    private double  price;
    private String tag;
    private String authorInfo;
    private String contentInfo;
    private String img;
    private int stock;

    public Book(){}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    @Basic
    @Column(name = "stock")
    public int getStock() {
        return stock;
    }

    @Basic
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    @Basic
    @Column(name = "book_name")
    public String getName() {
        return name;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    @Basic
    @Column(name = "book_isbn")
    public String getIsbn() {
        return isbn;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    @Basic
    @Column(name = "tag")
    public String  getTag() {
        return this.tag;
    }

    @Basic
    @Column(name = "authorInfo")
    public String getAuthorInfo() {return authorInfo;}

    @Basic
    @Column(name = "content")
    public String getContentInfo() {
        return contentInfo;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    public void setAuthorInfo(String authorInfo) {
        this.authorInfo = authorInfo;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

}
