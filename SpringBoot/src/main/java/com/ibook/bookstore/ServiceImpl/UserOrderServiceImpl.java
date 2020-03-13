package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Dao.OrderItemDao;
import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    private Order cart;

    private User user;

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

    @Override
    public Order buyFromCart(String name, List<OrderItem> orderItems) {
        double paidMoney = 0;
        ArrayList<OrderItem> ois = new ArrayList<>();
        /* Check the stock */
        for (OrderItem oi : orderItems) {
            OrderItem orderItem = orderItemDao.findOne(oi.getId());
            if (oi.getQuantity() > orderItem.getBook().getStock()) {
                return null;
            } else {
                ois.add(orderItem);
            }
        }
        if (cart == null) {
            // This should not happen
            cart = orderDao.findByUserUnpaid(name);
        }
        if (user == null) {
            user = userDao.findOne(name);
        }
        Order order = cart;
        order.setCreateDate(new Timestamp(System.currentTimeMillis()));
        for (OrderItem oi : ois) {
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
