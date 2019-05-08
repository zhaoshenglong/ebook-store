package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/order/{id}")
    public Order getOrderById(@PathVariable("id") String id) {
        return orderService.findOrderById(id);
    }
}
