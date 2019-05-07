package com.ibook.bookstore.Entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "users", schema = "bookstore", catalog = "")
public class User {
    private String name;
    private String email;
    private String password;
    private String avatar;
    private String briefAddr;
    private String detailAddr;
    private String state;

    public User() {}

    public String getAvatar() {
        return avatar;
    }

    public String getBriefAddr() {
        return briefAddr;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getState() {
        return state;
    }

    private Set<Order> orderSet;
    @OneToMany(fetch = FetchType.LAZY)
    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }
}
