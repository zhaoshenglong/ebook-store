package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Dao.AddressDao;
import com.ibook.bookstore.Entity.Address;
import com.ibook.bookstore.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl implements AddressDao {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findOne(String id) {return addressRepository.getOne(id);}

    @Override
    public Address updateProvince(Address address, String province) {
        address.setProvince(province);
        return addressRepository.save(address);
    }

    @Override
    public Address updateCity(Address address, String city) {
        address.setCity(city);
        return addressRepository.save(address);
    }

    @Override
    public Address updateDistrict(Address address, String district) {
        address.setDistrict(district);
        return addressRepository.save(address);
    }

    @Override
    public Address updateDetail(Address address, String detail) {
        address.setDetail(detail);
        return addressRepository.save(address);
    }
}
