package org.iBookStore.entity;

import java.util.ArrayList;

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

    public String getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public String getImg() {
        return img;
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
    public double getPrice() {
        return price;
    }
    public String  getTag() {
        return this.tag;
    }
    public String getAuthorInfo() {return authorInfo;}
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
