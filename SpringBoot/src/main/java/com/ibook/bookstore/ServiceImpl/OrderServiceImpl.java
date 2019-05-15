package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Dao.OrderItemDao;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    BookDao bookDao;

    @Override
    public Order findOrderById(String id) {
        return orderDao.findOne(id);
    }

    @Override
    public Page<Order> findAllOrderPage(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, 10);
        if (name.equals("admin")) {
            return orderDao.findAll(pageable);
        } else return orderDao.findAllByUserPaid(name, pageable);
    }

    @Override
    public Page<Order> findAllOrderBetween(String name, String beg, String end, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, 10);
        return orderDao.findAllByUserPaidBetween(name, Timestamp.valueOf(beg), Timestamp.valueOf(end), pageable);
    }

    @Override
    public Order addItemToCart(String name, Map<String, String> itemData) {
        String id = itemData.get("id");

        String orderId = itemData.get("orderId"), quantity = itemData.get("quantity"),
               bookPrice = itemData.get("bookPrice"), bookId = itemData.get("bookId");
        OrderItem orderItem;

        // Assumes that bookId is not null
        Book book = bookDao.findOne(bookId);
        if (id == null) {
            /* Maybe need to create a new order */
            orderItem = new OrderItem();
            if (orderId != null)
                orderItem.setOrderId(orderId);
            if (quantity != null)
                orderItem.setQuantity(Integer.parseInt(quantity));
            if (bookPrice != null)
                orderItem.setBookPrice(Double.parseDouble(bookPrice));
            orderItem.setBook(book);
        } else {
            /* Order item may exist */
            orderItem = orderItemDao.findOne(id);
            orderItem.setQuantity(orderItem.getQuantity() + Integer.parseInt(quantity));
        }
        Order cart = orderDao.findByUserUnpaid(name);
        if (orderItem.getOrderId() == null)
            orderItem.setOrderId(cart.getId());
        orderItemDao.saveItem(orderItem);
        return cart;
    }

    @Override
    public Order delItemFromCart(String name, String id) {
        orderItemDao.deleteItem(id);
        Order cart = orderDao.findByUserUnpaid(name);
        return cart;
    }

    @Override
    public Order buyFromCart(String name, List<OrderItem> orderItems) {
        Order order = new Order();
        Order cart = orderDao.findByUserUnpaid(name);
        order.getOrderItemList().addAll(orderItems);
        cart.getOrderItemList().removeAll(orderItems);
        return order;
    }

}
