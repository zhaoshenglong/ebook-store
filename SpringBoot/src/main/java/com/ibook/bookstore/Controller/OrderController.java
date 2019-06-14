package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/api/test/order/{id}")
    public Order getOrderById(@PathVariable("id") String id) {
        return orderService.findOrderById(id);
    }

    @GetMapping("/api/user/{name}/cart")
    public Order getCart(@PathVariable("name")String name) {
        return orderService.findCart(name);
    }

    @GetMapping("/api/user/{name}/orders")
    public Page<Order> getOrderPage(@PathVariable("name") String name,
                                    @RequestParam("page") String page) {
        return orderService.findUserOrderPage(name, Integer.parseInt(page), 10);
    }

    @GetMapping("/api/user/{name}/orders/between")
    public Page<Order> getOrderBetween(@PathVariable("name") String name,
                                       @RequestParam("beg") String beg,
                                       @RequestParam("end") String end,
                                       @RequestParam("page") Integer page){
        System.out.println(beg);
        System.out.println(end);
        return orderService.findUserOrderBetween(name, beg, end, page, 10);
    }

    @PostMapping("/api/user/{name}/orders/buy")
    public Order buyOrder(@PathVariable("name") String name,
                          @RequestBody List<OrderItem> orderItems) {
        return orderService.buyFromCart(name, orderItems);
    }

    @PutMapping("/api/user/{name}/orders/item/add")
    public Order addItem(@PathVariable("name") String name,
                         @RequestBody Map<String, String> data){
        return orderService.addItemToCart(name, data);
    }

    @PutMapping("/api/user/{name}/orders/item/set")
    public Order setItem(@PathVariable("name")String name,
                         @RequestBody Map<String, String> data) {
        return orderService.setItemQuantity(name, data);
    }

    @DeleteMapping("/api/user/{name}/orders/item/delete")
    public Order deleteOrder(@PathVariable("name") String name,
                             @RequestParam("id") String id) {
        return orderService.delItemFromCart(name, id);
    }

    @GetMapping("/api/admin/orders/option")
    public Page<Order> getAllOrderAdmin(@RequestParam("option")String option,
                                        @RequestParam("page") Integer page) {
        return orderService.getAdminOrders(option, page, 10);
    }

    @GetMapping("/api/admin/orders/statistics/all")
    public List<Order> getAll () {
        return orderService.findAll();
    }

    @GetMapping("/api/admin/orders/between")
    public Page<Order> getOrderBetween(@RequestParam("start")String start,
                                       @RequestParam("end")String end,
                                       @RequestParam("page")Integer page) {
        return orderService.getAdminOrderBetween(Timestamp.valueOf(start),Timestamp.valueOf(end), page, 10);
    }

    @GetMapping("/api/admin/orders/search")
    public List<Order> getOrderBetweenAdmin(@RequestParam(value = "user", required = false)String user,
                                            @RequestParam(value = "book", required = false)String book,
                                            @RequestParam(value = "start", required = false)Timestamp start,
                                            @RequestParam(value = "end", required = false)Timestamp end) {
        return orderService.getAdminOrderSearch(user, book, start, end);
    }
}
