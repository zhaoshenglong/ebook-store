package com.ibook.bookstore.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Service.AdminOrderService;
import com.ibook.bookstore.Service.UserOrderService;
import com.ibook.bookstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    WebApplicationContext applicationContext;

    private Gson gson = new GsonBuilder().create();

    @GetMapping("/api/test/order/{id}")
    public Order getOrderById(@PathVariable("id") String id) {
        UserOrderService userOrderService = applicationContext.getBean(UserOrderService.class);
        return userOrderService.findOrderById(id);
    }

    @GetMapping("/api/user/{name}/cart")
    public Order getCart(@PathVariable("name")String name) {
        UserOrderService userOrderService = applicationContext.getBean(UserOrderService.class);
        System.out.println(userOrderService);
        System.out.println(this);
        return userOrderService.findCart(name);
    }

    @GetMapping("/api/user/{name}/orders")
    public Page<Order> getOrderPage(@PathVariable("name") String name,
                                    @RequestParam("page") String page) {
        UserOrderService userOrderService = applicationContext.getBean(UserOrderService.class);
        return userOrderService.findUserOrderPage(name, Integer.parseInt(page), 10);
    }

    @GetMapping("/api/user/{name}/orders/between")
    public Page<Order> getOrderBetween(@PathVariable("name") String name,
                                       @RequestParam("beg") String beg,
                                       @RequestParam("end") String end,
                                       @RequestParam("page") Integer page){
        UserOrderService userOrderService = applicationContext.getBean(UserOrderService.class);
        return userOrderService.findUserOrderBetween(name, beg, end, page, 10);
    }

    @PostMapping("/api/user/{name}/orders/buy")
    public String buyOrder(@PathVariable("name") String name,
                          @RequestBody List<OrderItem> orderItems) {
        UserOrderService userOrderService = applicationContext.getBean(UserOrderService.class);
        userOrderService.buyFromCart(name, orderItems);
        return "Success";
    }

    @PutMapping("/api/user/{name}/orders/item/add")
    public Order addItem(@PathVariable("name") String name,
                         @RequestBody Map<String, String> data){
        UserOrderService userOrderService = applicationContext.getBean(UserOrderService.class);
        return userOrderService.addItemToCart(name, data);
    }

    @PutMapping("/api/user/{name}/orders/item/set")
    public Order setItem(@PathVariable("name")String name,
                         @RequestBody Map<String, String> data) {
        UserOrderService userOrderService = applicationContext.getBean(UserOrderService.class);
        return userOrderService.setItemQuantity(name, data);
    }

    @DeleteMapping("/api/user/{name}/orders/item/delete")
    public Order deleteItem(@PathVariable("name") String name,
                             @RequestParam("id") String id) {
        UserOrderService userOrderService = applicationContext.getBean(UserOrderService.class);
        return userOrderService.delItemFromCart(name, id);
    }

    @GetMapping("/api/admin/orders/option")
    public Page<Order> getAllOrderAdmin(@RequestParam("option")String option,
                                        @RequestParam("page") Integer page) {
        AdminOrderService adminOrderService = applicationContext.getBean(AdminOrderService.class);
        return adminOrderService.getAdminOrders(option, page, 10);
    }

    @GetMapping("/api/admin/orders/statistics/all")
    public List<Order> getAll () {
        AdminOrderService adminOrderService = applicationContext.getBean(AdminOrderService.class);
        return adminOrderService.findAll();
    }

    @GetMapping("/api/admin/orders/between")
    public Page<Order> getOrderBetween(@RequestParam("start")String start,
                                       @RequestParam("end")String end,
                                       @RequestParam("page")Integer page) {
        AdminOrderService adminOrderService = applicationContext.getBean(AdminOrderService.class);
        return adminOrderService.getAdminOrderBetween(Timestamp.valueOf(start),Timestamp.valueOf(end), page, 10);
    }

    @GetMapping("/api/admin/orders/search")
    public List<Order> getOrderBetweenAdmin(@RequestParam(value = "user", required = false)String user,
                                            @RequestParam(value = "book", required = false)String book,
                                            @RequestParam(value = "start", required = false)Timestamp start,
                                            @RequestParam(value = "end", required = false)Timestamp end) {
        AdminOrderService adminOrderService = applicationContext.getBean(AdminOrderService.class);
        return adminOrderService.getAdminOrderSearch(user, book, start, end);
    }

    /*
     * Websocket controller
     */
    @MessageMapping("/orders")
    @SendToUser("/topic/orders")
    public String orderMessage(@Payload String message, Principal principal ) throws InterruptedException {
        String name = gson.fromJson(message, Map.class).get("name").toString();
        System.out.println("Websocket receiver request from" + name);
        return "Hello " + System.currentTimeMillis();
    }
}
