package com.ibook.bookstore.ServiceImpl;

import com.google.common.base.Optional;
import com.ibook.bookstore.Dao.*;
import com.ibook.bookstore.Entity.*;
import com.ibook.bookstore.Service.UserService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;


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

    @Override
    public Boolean nameCanBeRegistered(String name) {
        Optional<User> optionalUser = userDao.findByName(name);
        User user = optionalUser.orNull();
        if (user != null) {
            /* User exists, can not be registered */
            return false;
        } else  {
            return true;
        }
    }

    @Override
    public Boolean emailCanBeRegistered(String email) {
        Optional<User> optionalUser = userDao.findByEmail(email);
        User user = optionalUser.orNull();
        if (user != null) {
            /* User can not be registered */
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String verifyPassword(HttpSession session, String password, HttpServletResponse response) {
        String template = "{\"msg\":%s}";
        String msg;
        String name = (String)session.getAttribute("name");
        if (name == null) {
            response.setStatus(401);
            msg = String.format(template, "user not logged in");
            return msg;
        } else {
            User user = userDao.findOne(name);
            if (passwordEncoder.matches(password ,user.getPassword())) {
                msg = String.format(template, "password verified");
                return msg;
            } else {
                response.setStatus(401);
                msg = String.format(template, "password not verified");
                return msg;
            }
        }

    }

    @Override
    public Map<String, String> getStatus(HttpSession session, HttpServletResponse response) {
        Map<String, String> status = new HashMap<>();
        if (session != null) {
            status.put("name", (String)session.getAttribute("name"));
            status.put("avatar", (String)session.getAttribute("avatar"));
            status.put("email", (String)session.getAttribute("email"));
            response.setStatus(200);
        } else {
            status.put("msg", "session not found");
            response.setStatus(401);
        }
        return status;
    }

    @Override
    public List<Map> statistics(Timestamp start, Timestamp end) {
        List<User> users = userDao.findAll();
        List<Order> orders = orderDao.findAllStatisticsBetween(start, end);
        List<Map> res = new ArrayList<>();

        if (orders == null) {
            System.out.println("null");
        } else {
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                User user = iterator.next();
                HashMap<String, Object> item = new HashMap<>();
                item.put("name", user.getName());
                item.put("totalConsume", user.getConsume());
                item.put("partConsume", new Double(0));
                for (Order order : orders) {
                    if (order.getUser().getName().equals(user.getName())) {
                        if (order.getPaid() != null) {
                            item.replace("partConsume", (Double)item.get("partConsume") + order.getPaid());
                        }
                        System.out.println(order.getUser());
                    }
                }
                res.add(item);
            }
        }
        return res;
    }


}
