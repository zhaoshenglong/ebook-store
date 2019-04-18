package org.iBookStore.entity;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private transient String id;
    //private String orderId;
    private String bookId;
    private int quantity;
    private Order order;
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBookId(String bookName) {
        this.bookId = bookName;
    }
    /*
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }*/

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
