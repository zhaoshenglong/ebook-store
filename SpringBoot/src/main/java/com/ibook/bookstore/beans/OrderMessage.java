package com.ibook.bookstore.beans;


import lombok.Data;
import java.sql.Timestamp;

@Data
public class OrderMessage {
    private String username;
    private Timestamp orderTime;
}
