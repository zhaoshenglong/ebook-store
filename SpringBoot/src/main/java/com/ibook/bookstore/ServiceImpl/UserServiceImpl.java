package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.AddressDao;
import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.Dao.OrderDao;
import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.Address;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Entity.Order;
import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User findUserByName(String name) {
        return  userDao.findOne(name);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Page<User> findUserByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userDao.findAll(pageable);
    }

    @Override
    public User createUser(User user) {
        user.setState(false);
        user.setCreateDate(new Timestamp(System.currentTimeMillis()));
        user.setModifyDate(new Timestamp(System.currentTimeMillis()));
        user.setAvatar(user.getAvatar() == null ? "http://localhost:8080/img?kind=user&name=default" : user.getAvatar());
        user.setAddresses(new HashSet<Address>());
        user.setLikedBooks(new HashSet<Book>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
        Order cart = new Order();
        cart.setState(0);
        cart.setUser(user);
        cart.setCreateDate(new Timestamp(System.currentTimeMillis()));
        orderDao.saveOrder(cart);
        return userDao.findOne(user.getName());
    }

    @Override
    public User likeBook(String name, String id) {
        User user = userDao.findOne(name);
        Book book = bookDao.findOne(id);
        book.setLiked(book.getLiked() + 1);
        user.getLikedBooks().add(book);
        return user;
    }

    @Override
    public void deleteUser(String name) {
        /**
         * delete address and order ?
         */
        userDao.deleteUser(name);

    }

    /*
     * Basic information updated here, address / liked books updated else where
     */
    @Override
    public User updateUser(Map<String, String> data) {
        String name = data.get("name");
        User user = userDao.findOne(name);
        String email = data.get("email"), password = data.get("password"),
               state = data.get("state");
        if (email != null)
            user.setEmail(email);
        if (password != null){
            user.setPassword(passwordEncoder.encode(password));
        }
        if (state != null)
            user.setState(Boolean.parseBoolean(state));
        user.setModifyDate(new Timestamp(System.currentTimeMillis()));
        return userDao.saveUser(user);
    }

    @Override
    public User addAddress(String name, Address address) {
        address.setUserName(name);
        address.setCreateDate(new Timestamp(System.currentTimeMillis()));
        address.setModifyDate(new Timestamp(System.currentTimeMillis()));
        /**
         * Does it save the address in Address ?
         */
        addressDao.saveAddress(address);
        return  userDao.findOne(name);
    }

    @Override
    public User updateAddress(Map<String, String> addressData){
        Address address = addressDao.findOne(addressData.get("id"));
        if (address == null) {
            return null;
        } else {
            User user = userDao.findOne(addressData.get("user"));
            String province = addressData.get("province"), city = addressData.get("city"),
                   district = addressData.get("district"), detail = addressData.get("detail");
            if (province != null)
                address.setProvince(province);
            if (city != null)
                address.setCity(city);
            if (district != null)
                address.setDistrict(district);
            if (detail != null)
                address.setDetail(detail);
            address.setModifyDate(new Timestamp(System.currentTimeMillis()));
            addressDao.saveAddress(address);
            return user;
        }
    }
    @Override
    public void deleteAddress(String id) {
        addressDao.deleteAddress(id);
    }


}
