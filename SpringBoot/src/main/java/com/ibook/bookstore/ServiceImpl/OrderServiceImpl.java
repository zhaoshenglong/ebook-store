package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Override
    public Order findOrderById(String id) {
        return orderDao.findOne(id);
    }
}
