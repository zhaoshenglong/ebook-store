package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;

public interface OrderDao {
    /* Query methods */
    Order findOne(String id);
    Page<Order> findAll(Pageable pageable);
    Page<Order> findAllByUser(String user, Pageable pageable);
    Page<Order> findAllByUserPaidBetween(Timestamp beg, Timestamp end, Pageable pageable);
    Page<Order> findAllByUserPaid(String user, Pageable pageable);
    Order findByUserUnpaid(String user);
    Page<Order> findAllByState(int state, Pageable pageable);
    Page<Order> findAllBetween(Timestamp beg, Timestamp end, Pageable pageable);

    /* Create methods */
    Order createOne(Order order);

    /* Update methods */
    Order updateOrderState(Order order, int state);
    Order addOrderItem(Order order, OrderItem orderItem);
    Order deleteOrderItem(Order order, OrderItem orderItem);

    /* Delete methods */
    Order deleteOrder(Order order);
}
