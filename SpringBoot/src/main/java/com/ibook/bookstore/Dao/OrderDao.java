package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Order;

public interface OrderDao {
    Order findOne(String id);
}
