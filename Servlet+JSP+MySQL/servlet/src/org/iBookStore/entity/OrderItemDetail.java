package org.iBookStore.entity;

import java.util.Map;
import java.util.Set;

public class OrderItemDetail {
    private Book book;
    private int quantity;
    public OrderItemDetail(){}

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public Book getBook() {
        return book;
    }
}
