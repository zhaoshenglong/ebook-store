package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Dao.OrderItemDao;
import com.ibook.bookstore.Dao.UserDao;
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

    @Autowired
    UserDao userDao;
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

        String quantity = itemData.get("quantity"),
               bookId = itemData.get("bookId");
        OrderItem orderItem = null;
        Order cart = orderDao.findByUserUnpaid(name);

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
            }
        } else {
            /* Order item may exist */
            orderItem = orderItemDao.findOne(id);
            orderItem.setQuantity(orderItem.getQuantity() + Integer.parseInt(quantity));
        }
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
        order.setCreateDate(new Timestamp(System.currentTimeMillis()));
        order.setUser(userDao.findOne(name));
        order.setState(1);
        orderDao.saveOrder(order);
        for (OrderItem oi : orderItems) {
            OrderItem orderItem = orderItemDao.findOne(oi.getId());
            orderItem.setOrderId(order.getId());
            if (oi.getQuantity() > orderItem.getBook().getStock()) {
                return null;
            } else {
                Book book = orderItem.getBook();
                book.setStock(book.getStock() - oi.getQuantity());
                orderItem.setBookPrice(book.getPrice());
                bookDao.saveBook(book);
            }
            orderItemDao.saveItem(orderItem);
        }
        return order;
    }

    @Override
    public Order findCart(String name) {
        Order cart = orderDao.findByUserUnpaid(name);
        return cart;
    }

    @Override
    public Order setItemQuantity(String name, Map<String, String> data) {
        Order cart = orderDao.findByUserUnpaid(name);
        String itemId = data.get("id"), quantity = data.get("quantity");
        if (itemId == null || quantity == null) {
            return null;
        } else {
            OrderItem orderItem = orderItemDao.findOne(itemId);
            orderItem.setQuantity(Integer.parseInt(quantity));
            orderItemDao.saveItem(orderItem);
        }
        return cart;
    }

}
