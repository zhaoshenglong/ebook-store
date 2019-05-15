package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Address;

public interface AddressDao {
    Address findOne(String id);

    Address saveAddress(Address address);
    void deleteAddress(String id);
}
