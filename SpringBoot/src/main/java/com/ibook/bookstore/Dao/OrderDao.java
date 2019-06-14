package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface OrderDao {
    /* Query methods */
    Order findOne(String id);
    Page<Order> findAll(Pageable pageable);
    Page<Order> findAllByUser(String user, Pageable pageable);
    Page<Order> findAllByUserPaidBetween(String name, Timestamp beg, Timestamp end, Pageable pageable);
    Page<Order> findAllByUserPaid(String user, Pageable pageable);
    Order findByUserUnpaid(String user);
    Page<Order> findAllByState(int state, Pageable pageable);
    Page<Order> findAllBetween(Timestamp beg, Timestamp end, Pageable pageable);
    List findAll();
    /* Create methods */
    Order saveOrder(Order order);

    /* Delete methods */
    Order deleteOrder(Order order);
}
