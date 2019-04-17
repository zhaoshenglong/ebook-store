package org.iBookStore.entity;

import java.sql.Date;

public class Order {
    private String id;
    private String userName;
    private Date createDate;
    private String state;
    public Order() {}

    public String getState() {
        return state;
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
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setState(String state) {
        this.state = state;
    }
}
