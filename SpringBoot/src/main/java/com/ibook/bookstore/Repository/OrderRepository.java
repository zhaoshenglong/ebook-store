package com.ibook.bookstore.Repository;

import com.ibook.bookstore.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
