package org.iBookStore;

import java.util.ArrayList;

public class Book {
    private String name;
    private String author;
    private String isbn;
    private String src;
    private double  price;
    private ArrayList<String> tags;

    Book(String name, String author, String isbn, String src, double price) {
        this.name = name;
        this.author = author;
        this.src = src;
        this.isbn = isbn;
        this.price = price;
        this.tags = new ArrayList<String>();
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
    public ArrayList<String> getTags() {
        return this.tags;
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
    public void addTag(String t) {
        this.tags.add(t);
    }
    public void delTag(String t) {
        this.tags.remove(this.tags.indexOf(t));
    }
}
