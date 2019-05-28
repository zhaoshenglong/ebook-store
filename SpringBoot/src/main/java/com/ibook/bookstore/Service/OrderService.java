package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order findOrderById(String id);

    Page<Order> findUserOrderPage(String name, Integer page, Integer size);

    Page<Order> findUserOrderBetween(String name, String beg, String end, Integer page, Integer size);

    Order addItemToCart(String name, Map<String, String> itemData);

    Order delItemFromCart(String name, String id);

    Order buyFromCart(String name, List<OrderItem> orderItems);

    Order findCart(String name);

    Order setItemQuantity(String name, Map<String, String> data);

    Page<Order> getAdminOrders(String option, int page, int size);

    Page<Order> getAdminOrderBetween(Timestamp start, Timestamp end, int page, int size);
}
