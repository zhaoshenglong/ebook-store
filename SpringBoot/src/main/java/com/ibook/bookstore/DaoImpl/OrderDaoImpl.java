package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order findOne(String id) {
        return orderRepository.getOne(id);
    }
}
