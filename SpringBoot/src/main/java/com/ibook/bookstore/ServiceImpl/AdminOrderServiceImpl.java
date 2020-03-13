package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Dao.OrderItemDao;
import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    UserDao userDao;

    @Override
    public List findAll() {
        return orderDao.findAll();
    }

    @Override
    public Page<Order> getAdminOrders(String option, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        switch (option) {
            case "all":
                return orderDao.findAll(pageable);
            case "paid":
                return orderDao.findAllByState(1, pageable);
            case "unpaid":
                return orderDao.findAllByState(0, pageable);
            case "deleted":
                return orderDao.findAllByState(2, pageable);
            default:
                return null;
        }
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
            orders.removeIf(order -> !order.getUser().getName().contains(user));
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
