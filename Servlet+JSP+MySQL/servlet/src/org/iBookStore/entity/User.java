package org.iBookStore.entity;

public class User
{
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

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBriefAddr(String briefAddr) {
        this.briefAddr = briefAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setState(String state) {
        this.state = state;
    }
}
