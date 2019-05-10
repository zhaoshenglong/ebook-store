package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDaoImpl {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem updateQuantity(OrderItem orderItem, int quantity) {
        return orderItemRepository.save(orderItem);
    }
}
