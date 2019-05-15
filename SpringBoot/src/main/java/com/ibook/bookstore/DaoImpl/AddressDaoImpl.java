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
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(String id){
        addressRepository.deleteById(id);
    }
}
