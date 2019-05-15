package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Dao.OrderItemDao;
import com.ibook.bookstore.Entity.OrderItem;
import com.ibook.bookstore.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem updateQuantity(OrderItem orderItem, int quantity) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteItem(String id ){
        orderItemRepository.deleteById(id);
    }

    @Override
    public OrderItem findOne(String id) {
        return orderItemRepository.getOne(id);
    }

    @Override
    public OrderItem saveItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
