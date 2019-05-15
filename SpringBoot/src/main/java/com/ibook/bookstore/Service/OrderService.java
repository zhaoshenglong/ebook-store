package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order findOrderById(String id);

    Page<Order> findAllOrderPage(String name, Integer page, Integer size);

    Page<Order> findAllOrderBetween(String name, String beg, String end, Integer page, Integer size);

    Order addItemToCart(String name, Map<String, String> itemData);

    Order delItemFromCart(String name, String id);

    Order buyFromCart(String name, List<OrderItem> orderItems);
}
