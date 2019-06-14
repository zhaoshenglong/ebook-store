package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Dao.OrderItemDao;
import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Service.OrderService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
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
    public Page<Order> findUserOrderPage(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, 10);
        if (name.equals("admin")) {
            return orderDao.findAll(pageable);
        } else return orderDao.findAllByUserPaid(name, pageable);
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
        Double paidMoney = new Double(0);
        /* Check the stock */
        for (OrderItem oi : orderItems) {
            OrderItem orderItem = orderItemDao.findOne(oi.getId());
            if (oi.getQuantity() > orderItem.getBook().getStock()) {
                return null;
            }
        }
        Order order = new Order();
        order.setCreateDate(new Timestamp(System.currentTimeMillis()));
        order.setUser(userDao.findOne(name));
        order.setState(1);
        User user = userDao.findOne(name);
        orderDao.saveOrder(order);
        for (OrderItem oi : orderItems) {
            OrderItem orderItem = orderItemDao.findOne(oi.getId());
            orderItem.setOrderId(order.getId());
            Book book = orderItem.getBook();
            book.setStock(book.getStock() - oi.getQuantity());
            orderItem.setBookPrice(book.getPrice());
            paidMoney += orderItem.getBookPrice() * orderItem.getQuantity();
            user.setConsume(user.getConsume() + paidMoney);
            userDao.saveUser(user);
            bookDao.saveBook(book);
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

    @Override
    public Page<Order> getAdminOrders(String option, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (option.equals("all")) {
            return orderDao.findAll(pageable);
        } else if (option.equals("paid")) {
            return orderDao.findAllByState(1, pageable);
        } else if (option.equals("unpaid")) {
            return orderDao.findAllByState(0, pageable);
        } else if (option.equals("deleted")) {
            return orderDao.findAllByState(2, pageable);
        } else return null;
    }

    @Override
    public Page<Order> getAdminOrderBetween(Timestamp start, Timestamp end, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderDao.findAllBetween(start,end,pageable);
    }

    @Override
    public List<Order> getAdminOrderSearch(String user, String book, Timestamp start, Timestamp end) {
        List<Order> orders = orderDao.findAll();
        System.out.println(user);
        System.out.println(book);
        System.out.println(start);
        System.out.println(end);
        if (user != null) {
            Iterator<Order> iterator = orders.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().getUser().getName().contains(user)) {
                    iterator.remove();
                }
            }
        }
        if (book != null) {
            Iterator<Order> iterator = orders.iterator();
            while (iterator.hasNext()) {
                Iterator<OrderItem> itemIterator = iterator.next().getOrderItemList().iterator();
                boolean retain = false;
                while(itemIterator.hasNext()) {
                    Book b = itemIterator.next().getBook();
                    if (b.getName().contains(book) ||
                        b.getIsbn().contains(book) ||
                        b.getAuthor().contains(book)) {
                        retain = true;
                        break;
                    }
                }
                if (!retain) {
                    iterator.remove();
                }
            }
        }
        if (start != null) {
            if (end == null) {
                end = new Timestamp(System.currentTimeMillis());
            }
            Iterator<Order> iterator = orders.iterator();
            while (iterator.hasNext()) {
                Order o = iterator.next();
                if ( !(o.getCreateDate().after(start) && o.getCreateDate().before(end) )) {
                    iterator.remove();
                }
            }
        }
        return orders;
    }

}
