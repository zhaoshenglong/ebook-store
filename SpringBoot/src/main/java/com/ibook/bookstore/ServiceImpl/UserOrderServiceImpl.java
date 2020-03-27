package com.ibook.bookstore.ServiceImpl;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Service
@Scope("session")
public class UserOrderServiceImpl implements UserOrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    UserDao userDao;

    @Autowired
    private KafkaTemplate<String, String>kafkaTemplate;

    private Order cart;

    private User user;

    private Gson gson = new GsonBuilder().create();

    @Override
    public Order findOrderById(String id) {
        return orderDao.findOne(id);
    }

    @Override
    public Page<Order> findUserOrderPage(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, 10);
        return orderDao.findAllByUserPaid(name, pageable);
    }

    @Override
    public Page<Order> findUserOrderBetween(String name, String beg, String end, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, 10);
        return orderDao.findAllByUserPaidBetween(name, Timestamp.valueOf(beg), Timestamp.valueOf(end), pageable);
    }

    @Override
    public Order addItemToCart(String name, Map<String, String> itemData) {
        String id = itemData.get("id");

        String quantity = itemData.get("quantity"),
                bookId = itemData.get("bookId");
        OrderItem orderItem = null;
        if (cart == null) {
            cart = orderDao.findByUserUnpaid(name);
        }
        // Assumes that bookId is not null
        if (id == null) {
            for (OrderItem oi :cart.getOrderItemList() ) {
                if (oi.getBook().getId().equals(bookId)) {
                    orderItem = oi;
                    break;
                }
            }
            if (orderItem != null) {
                orderItem.setQuantity(orderItem.getQuantity() + Integer.parseInt(quantity));
            } else {
                orderItem = new OrderItem();
                if (quantity != null)
                    orderItem.setQuantity(Integer.parseInt(quantity));
                Book book = bookDao.findOne(bookId);
                orderItem.setBook(book);
                orderItem.setBookPrice(book.getPrice());
                cart.getOrderItemList().add(orderItem);
            }
        } else {
            /* Order item may exist */
            orderItem = orderItemDao.findOne(id);
            orderItem.setQuantity(orderItem.getQuantity() + Integer.parseInt(quantity));
            cart.getOrderItemList().add(orderItem);
        }
        if (orderItem.getOrderId() == null)
            orderItem.setOrderId(cart.getId());
        orderItemDao.saveItem(orderItem);
        return cart;
    }



    @Override
    public Order delItemFromCart(String name, String id) {
        if (cart == null) {
            cart = orderDao.findByUserUnpaid(name);
        }
        for(OrderItem oi: cart.getOrderItemList()) {
            if (oi.getId().equals(id)) {
                cart.getOrderItemList().remove(oi);
            }
        }
        orderItemDao.deleteItem(id);
        return cart;
    }

    /*
     * Produce message, put into kafka MQ
     */
    @Override
    public Order buyFromCart(String name, List<OrderItem> orderItems) {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrderTime(new Timestamp(System.currentTimeMillis()));
        orderMessage.setUsername(name);
        kafkaTemplate.send("order", gson.toJson(orderMessage));
        return cart;
    }

    @KafkaListener(topics = {"orderCallback"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if(kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            Order order = gson.fromJson((String)message, Order.class);
            if(user == null) {
                user = order.getUser();
            }
            cart = orderDao.findByUserUnpaid(user.getName());
            System.out.println("Receive Message after buy order" + message);
        }
    }

    @Override
    public Order findCart(String name) {
        if (cart == null){
            cart = orderDao.findByUserUnpaid(name);
        }
        return cart;
    }

    @Override
    public Order setItemQuantity(String name, Map<String, String> data) {
        if (cart == null){
            cart = orderDao.findByUserUnpaid(name);
        }
        String itemId = data.get("id"), quantity = data.get("quantity");
        if (itemId == null || quantity == null) {
            return null;
        } else {
            OrderItem orderItem = orderItemDao.findOne(itemId);
            orderItem.setQuantity(Integer.parseInt(quantity));
            orderItemDao.saveItem(orderItem);
            for (OrderItem oi: cart.getOrderItemList()) {
                if (oi.getId().equals(itemId)) {
                    oi.setQuantity(Integer.parseInt(quantity));
                }
            }
        }
        return cart;
    }
}
