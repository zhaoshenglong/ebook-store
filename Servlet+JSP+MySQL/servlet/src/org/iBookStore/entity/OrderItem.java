package org.iBookStore.entity;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private transient String id;
    private String bookId;
    private int quantity;
    /* Transient for serialization of Gson, or there may be dead loop */
    private transient Order order;
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBookId(String bookName) {
        this.bookId = bookName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public String getId() {
        return id;
    }
}
