package com.ibook.bookstore.Dao;

import com.ibook.bookstore.Entity.Address;

public interface AddressDao {
    Address findOne(String id);

    Address updateProvince(Address address, String province);
    Address updateCity(Address address, String city);
    Address updateDistrict(Address address, String district);
    Address updateDetail(Address address, String detail);
}
