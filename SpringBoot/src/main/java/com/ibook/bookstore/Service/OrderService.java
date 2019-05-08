package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.Order;

public interface OrderService {
    Order findOrderById(String id);
}
