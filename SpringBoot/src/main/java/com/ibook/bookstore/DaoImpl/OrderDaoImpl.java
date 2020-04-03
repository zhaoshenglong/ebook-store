package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Order findOne(String id) {
        return orderRepository.getOne(id);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Order> findAllByUser(String name, Pageable pageable) {
        return orderRepository.findAllByUser_Name(name, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Order> findAllByUserPaidBetween(String name, Timestamp beg, Timestamp end, Pageable pageable) {
        return orderRepository.findAllByUser_NameAndCreateDateBetweenAndStateEquals(name, beg, end, 1, pageable);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Order> findAllByUserPaid(String user, Pageable pageable) {
        return orderRepository.findAllByUser_NameAndStateEquals(user, 1, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Order> findAllByState(int state, Pageable pageable) {
        return orderRepository.findAllByStateEquals(state, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Order> findAllBetween(Timestamp start, Timestamp end, Pageable pageable) {
        return orderRepository.findAllByCreateDateBetween(start, end, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List findAllStatisticsBetween(Timestamp beg, Timestamp end) {
        System.out.println(beg);
        System.out.println(end);
        return orderRepository.findAllByCreateDateBetween(beg, end);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Order findByUserUnpaid(String user) {
        return orderRepository.findOrderByUser_NameAndStateEquals(user, 0);
    }

    /* save or update order */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Order deleteOrder(Order order) {
        // 2 -> deleted
        order.setState(2);
        return orderRepository.save(order);
    }

}
