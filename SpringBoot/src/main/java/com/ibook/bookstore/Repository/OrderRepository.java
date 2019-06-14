package com.ibook.bookstore.Repository;

import com.ibook.bookstore.Entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findAllByUser_Name(String name, Pageable pageable);
    Page<Order> findAllByUser_NameAndCreateDateBetweenAndStateEquals(String name, Timestamp beg, Timestamp end, int state, Pageable pageable);
    Page<Order> findAllByCreateDateBetween(Timestamp start, Timestamp end, Pageable pageable);
    List<Order> findAllByCreateDateBetween(Timestamp start, Timestamp end);
    Page<Order> findAllByUser_NameAndStateEquals(String name, int state, Pageable pageable);
    Page<Order> findAllByStateEquals(int state, Pageable pageable);
    Order findOrderByUser_NameAndStateEquals(String name, int state);
}
