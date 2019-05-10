package com.ibook.bookstore.Repository;

import com.ibook.bookstore.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
}
