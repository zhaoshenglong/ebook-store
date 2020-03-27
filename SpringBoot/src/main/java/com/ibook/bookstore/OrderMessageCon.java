package com.ibook.bookstore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Dao.OrderItemDao;
import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Service.UserOrderService;
import com.ibook.bookstore.beans.OrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;

@Component
public class OrderMessageCon {
    private Gson gson = new GsonBuilder().create();
    @Autowired
    OrderDao orderDao;
    @Autowired
    UserDao userDao;
    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = {"order"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if(kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            OrderMessage orderMessage = gson.fromJson((String)message, OrderMessage.class);
            String name = orderMessage.getUsername();
            Timestamp orderDate = orderMessage.getOrderTime();
            Order order = buyOrder(name, orderDate);

            kafkaTemplate.send("orderCallback", gson.toJson(order));
        }
    }

    Order buyOrder(String name, Timestamp orderDate) {
        double paidMoney = 0;
        Order cart = orderDao.findByUserUnpaid(name);
        User user = userDao.findOne(name);
        Order order = cart;
        order.setCreateDate(orderDate);
        for (OrderItem oi : cart.getOrderItemList()) {
            Book book = oi.getBook();
            book.setStock(book.getStock() - oi.getQuantity());
            book.setSale(book.getSale() + oi.getQuantity());
            oi.setBookPrice(book.getPrice());
            paidMoney += oi.getBookPrice() * oi.getQuantity();
            bookDao.saveBook(book);
            orderItemDao.saveItem(oi);
        }
        user.setConsume(user.getConsume() + paidMoney);
        userDao.saveUser(user);
        order.setState(1);
        order.setPaid(paidMoney);
        orderDao.saveOrder(order);

        // create new cart
        cart = new Order();
        cart.setOrderItemList(new HashSet<>());
        cart.setState(0);
        cart.setUser(user);
        cart.setCreateDate(new Timestamp(System.currentTimeMillis()));
        orderDao.saveOrder(cart);
        return order;
    }
}
