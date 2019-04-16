package org.iBookStore.entity;

import java.sql.Date;
import java.util.ArrayList;

public class Order {
    private String id;
    private String userName;
    private Date createDate;
    private String state;
    private ArrayList<Book> orderItem;
    public Order() {}

    public String getState() {
        return state;
    }

    public ArrayList<Book> getOrderItem() {
        return orderItem;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrderItem(ArrayList<Book> orderItem) {
        this.orderItem = orderItem;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setState(String state) {
        this.state = state;
    }
}
