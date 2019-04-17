package org.iBookStore.entity;

import java.util.ArrayList;

public class Book {
    private String id;
    private String name;
    private String author;
    private String isbn;
    private String src;
    private double  price;
    private String tag;
    private String authorInfo;
    private String content;
    public String img;
    public Book(){}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getSrc() {
        return src;
    }
    public double getPrice() {
        return price;
    }
    public String  getTag() {
        return this.tag;
    }
    public String getAuthorInfo() {return authorInfo;}
    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setSrc(String src) {
        this.src = src;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
}
