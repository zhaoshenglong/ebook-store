package com.ibook.bookstore.Dao;


import com.ibook.bookstore.Entity.OrderItem;

public interface OrderItemDao {
    /* modify orderItem */
    OrderItem updateQuantity(OrderItem orderItem, int quantity);
}
