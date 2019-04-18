package org.iBookStore.entity;


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class Order {
    private String id;
    private String userName;
    private Timestamp createDate;
    private String state;
    private Set<OrderItem> orderItemList;
    public Order() {}

    public Set<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public String getState() {
        return state;
    }


    public Timestamp getCreateDate() {
        return createDate;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOrderItemList(Set<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public void setState(String state) {
        this.state = state;
    }
}
