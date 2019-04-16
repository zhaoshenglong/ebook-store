package org.iBookStore.entity;

public class ErrorJson {
    private String status;
    private String msg;
    public ErrorJson() {}
    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
