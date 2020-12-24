package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.Order;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;

public interface AdminOrderService {
    List<Order> findAll();

    Page<Order> getAdminOrders(String option, int page, int size);

    Page<Order> getAdminOrderBetween(Timestamp start, Timestamp end, int page, int size);

    List<Order> getAdminOrderSearch(String user, String book, Timestamp start, Timestamp end);
}
